package com.whm.common.mybatis.plugins;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;

/**
 * 用于输出每条 SQL 语句及其执行时间
 *
 * @author 吴华明
 * @date 2025/9/6 11:41
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = Statement.class),
        @Signature(type = StatementHandler.class, method = "batch", args = Statement.class)
})
public class SqlLogInterceptor implements Interceptor {
    private static final String DRUID_POOLED_PREPARED_STATEMENT = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4C_PREPARED_STATEMENT = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String SHARDINGSPHERE_PREPARED_STATEMENT = "io.shardingsphere.core.jdbc.core.statement.MasterSlavePreparedStatement";
    private static final String ORACLE_PREPARED_STATEMENT_WRAPPER = "oracle.jdbc.driver.OraclePreparedStatementWrapper";

    private static final String STMT_STATEMENT = "stmt.statement";

    private static final String DELEGATE = "delegate";

    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSqlMethod;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            if (stmtMetaObj.hasGetter(STMT_STATEMENT)) {
                statement = (Statement) stmtMetaObj.getValue("stmt.statement");
            } else if (stmtMetaObj.hasGetter(DELEGATE)) {
                //Hikari
                statement = (Statement) stmtMetaObj.getValue("delegate");
            }else {
                statement = (Statement) stmtMetaObj.getValue("targetPreparedStatement.stmt");
            }
        } catch (Exception e) {
            log.error("SQL拦截异常1!", e);
        }

        String originalSql = null;
        String stmtClassName = statement.getClass().getName();
        if (DRUID_POOLED_PREPARED_STATEMENT.equals(stmtClassName)) {
            try {
                if (druidGetSqlMethod == null) {
                    Class<?> clazz = Class.forName(DRUID_POOLED_PREPARED_STATEMENT);
                    druidGetSqlMethod = clazz.getMethod("getSql");
                }
                Object stmtSql = druidGetSqlMethod.invoke(statement);
                if (stmtSql instanceof String) {
                    originalSql = (String) stmtSql;
                }
            } catch (Exception e) {
                log.error("SQL拦截异常3!", e);
            }
        } else if (T4C_PREPARED_STATEMENT.equals(stmtClassName)
                || ORACLE_PREPARED_STATEMENT_WRAPPER.equals(stmtClassName)) {
            try {
                if (oracleGetOriginalSqlMethod != null) {
                    Object stmtSql = oracleGetOriginalSqlMethod.invoke(statement);
                    if (stmtSql instanceof String) {
                        originalSql = (String) stmtSql;
                    }
                } else {
                    Class<?> clazz = Class.forName(stmtClassName);
                    oracleGetOriginalSqlMethod = getMethodRegular(clazz, "getOriginalSql");
                    if (oracleGetOriginalSqlMethod != null) {
                        // OraclePreparedStatementWrapper is not a public class, need set this.
                        // oracleGetOriginalSqlMethod.setAccessible(true);
                        ReflectionUtils.makeAccessible(oracleGetOriginalSqlMethod);
                        if (null != oracleGetOriginalSqlMethod) {
                            Object stmtSql = oracleGetOriginalSqlMethod.invoke(statement);
                            if (stmtSql instanceof String) {
                                originalSql = (String) stmtSql;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("SQL拦截异常4!", e);
            }
        } else if (SHARDINGSPHERE_PREPARED_STATEMENT.equals(stmtClassName)) {
            try {
                Class clazz = Class.forName(stmtClassName);
                Class<?> pool = Class.forName(DRUID_POOLED_PREPARED_STATEMENT);
                Method method = this.getMethodRegular(clazz, "getRoutedStatements");
                List<Object> objectList = (List<Object>) method.invoke(statement);
                if (CollectionUtils.isNotEmpty(objectList)) {
                    final String stmtSql = BeanUtil.getFieldValue(objectList.get(0), "stmt") + "";
                    originalSql = stmtSql;
                }
            } catch (ClassNotFoundException e) {
                log.error("SQL拦截异常5!", e);
            } catch (IllegalAccessException e) {
                log.error("SQL拦截异常6!", e);
            } catch (IllegalArgumentException e) {
                log.error("SQL拦截异常7!", e);
            } catch (InvocationTargetException e) {
                log.error("SQL拦截异常8!", e);
            }
        }
        if (originalSql == null) {
            originalSql = statement.toString();
        }
        originalSql = originalSql.replaceAll("[\\s]+", StringPool.SPACE);
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }

        // 计算执行 SQL 耗时
        long start = SystemClock.now();
        Object result = invocation.proceed();
        long timing = SystemClock.now() - start;

        // SQL 打印执行结果
        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        boolean pringSqlLogFlag = true;
        //判断是否打印sql
        if (pringSqlLogFlag) {
            // 打印 sql
            if (isLocalDev()) {
                System.err.println(
                        StrUtil.format(
                                "\n==============  Sql Start  ==============" +
                                        "\nExecute ID  ：{}" +
                                        "\nExecute SQL ：{}" +
                                        "\nExecute Time：{} ms" +
                                        "\n==============  Sql  End   ==============\n",
                                ms.getId(), originalSql, timing));
            } else {
                log.info("\n==============  Sql Start  ==============" +
                        "\nExecute ID  ：{}" +
                        "\nExecute SQL ：{}" +
                        "\nExecute Time：{} ms" +
                        "\n==============  Sql  End   ==============\n", ms.getId(), originalSql, timing);
            }
        }

        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 获取此方法名的具体 Method
     *
     * @param clazz      class 对象
     * @param methodName 方法名
     * @return 方法
     */
    private Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return getMethodRegular(clazz.getSuperclass(), methodName);
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql ignore
     * @return ignore
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }

    /**
     * 代码部署于 linux 上，工作默认为 mac 和 Windows
     */
    public static final String OS_NAME_LINUX = "LINUX";

    /**
     * 判断是否为本地开发环境
     *
     * @return boolean
     */
    public static boolean isLocalDev() {
        String osName = System.getProperty("os.name");
        return StringUtils.hasText(osName) && !(OS_NAME_LINUX.equals(osName.toUpperCase()));
    }

}