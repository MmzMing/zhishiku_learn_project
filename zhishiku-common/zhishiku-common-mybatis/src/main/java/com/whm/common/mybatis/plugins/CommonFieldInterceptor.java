package com.whm.common.mybatis.plugins;


import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.whm.common.core.context.SecurityContextHolder;
import com.whm.common.mybatis.config.IgnoreTableConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 创建人信息 / 修改人信息
 *
 * @author : 吴华明
 * @since 2025-11-27  19:41
 */
@Slf4j
public class CommonFieldInterceptor extends JsqlParserSupport implements InnerInterceptor {

    //@Autowired
    private IgnoreTableConfig ignoreTableConfig;

    final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String CREATE_BY = "create_by";
    private static final String CREATE_NAME = "create_name";
    private static final String CREATE_TIME = "create_time";
    private static final String UPDATE_BY = "update_by";
    private static final String UPDATE_NAME = "update_name";
    private static final String UPDATE_TIME = "update_time";
    private static final String DELETED = "deleted";


    /**
     * 在 SQL 预处理之前执行的回调方法，用于拦截 MyBatis 的 StatementHandler，
     * 对 INSERT 和 UPDATE 类型的 SQL 语句进行自定义处理。
     *
     * <p>该方法主要完成以下工作：
     * <ul>
     *   <li>判断 SQL 类型是否为 INSERT 或 UPDATE；</li>
     *   <li>使用 JSqlParser 解析原始 SQL 语句并提取涉及的表名；</li>
     *   <li>根据配置忽略特定表的操作；</li>
     *   <li>对符合条件的 SQL 进行进一步解析与改写；</li>
     * </ul>
     *
     * @param sh                 MyBatis 的 StatementHandler 实例，用于获取当前执行的 SQL 相关信息；
     * @param connection         当前数据库连接对象；
     * @param transactionTimeout 事务超时时间（单位：秒），可能为 null；
     */
    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();

        // 只对新增和更新进行处理
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE) {

            BoundSql boundSql = sh.getBoundSql();
            String sql = boundSql.getSql();

            // 使用 JsqlParser 解析 SQL 语句
            Statement statement;
            try {
                statement = CCJSqlParserUtil.parse(sql);
            } catch (JSQLParserException e) {
                log.warn("JsqlParser 解析 SQL 语句异常: {}", sql, e);
                // 处理解析异常
                return;
            }

            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List<String> tableNames = tablesNamesFinder.getTableList(statement);

            // 定义需要忽略处理的表列表
            List<String> tables = Arrays.asList("sys_user_role", "sys_oper_log","sys_login_infor");
            // TODO 后面配置忽略表 nacos配置
            //List<String> tables = ignoreTableConfig.getTables();

            // 判断当前 SQL 中是否存在被忽略的表
            if (shouldIgnoreTable(tableNames, tables)) {
                return;
            }
            // 对 SQL 进行处理
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            try {
                // 初始化上下文环境
                ContextHolder.init();
                // 检查是否需要处理，并对 SQL 做多组织等逻辑处理
                mpBs.sql(parserMulti(mpBs.sql(), null));
            } finally {
                // 将已处理的 MappedStatement 加入缓存并清理上下文
                addMappedStatementCache(ms);
                ContextHolder.clear();
            }
        }
    }

    /**
     * 判断是否应该忽略处理指定的表
     *
     * @param tableNames    SQL中涉及的表名列表
     * @param ignoredTables 配置的需要忽略处理的表列表
     * @return 如果应该忽略处理返回true，否则返回false
     */
    private boolean shouldIgnoreTable(List<String> tableNames, List<String> ignoredTables) {
        for (String tableName : tableNames) {
            // 精确匹配
            if (ignoredTables.contains(tableName)) {
                return true;
            }

            // 模糊匹配 - 检查是否有以 ** 结尾的模糊匹配规则
            for (String ignoredTable : ignoredTables) {
                if (ignoredTable.endsWith("**") && tableName.startsWith(StringUtils.removeEnd(ignoredTable, "**"))) {
                    return true;
                }
            }
        }
        return false;
    }


        /**
     * 处理INSERT语句，在插入数据时自动添加创建人、创建时间、更新人、更新时间等公共字段
     *
     * @param insert INSERT语句对象
     * @param index 语句在批处理中的索引位置
     * @param sql 原始SQL语句
     * @param obj 相关的对象参数
     */
    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        if (!(insert.getItemsList() instanceof ExpressionList)) {
            return;
        }
        ExpressionList itemsList = insert.getItemsList(ExpressionList.class);

        // 获取插入语句中的列列表
        List<Column> columns = insert.getColumns();

        Set<String> existingColumns = new HashSet<>();
        for (Column column : columns) {
            existingColumns.add(column.getColumnName());
        }
        TimestampValue timestampValue = new TimestampValue(LocalDateTime.now().format(FORMATTER));

        // 统一处理所有需要添加的字段，避免重复代码
        addColumnIfNotExists(existingColumns, itemsList, insert, CREATE_BY, new StringValue(getUserName()));
        addColumnIfNotExists(existingColumns, itemsList, insert, CREATE_NAME, new StringValue(getNickName()));
        addColumnIfNotExists(existingColumns, itemsList, insert, CREATE_TIME, timestampValue);
        addColumnIfNotExists(existingColumns, itemsList, insert, UPDATE_BY, new StringValue(getUserName()));
        addColumnIfNotExists(existingColumns, itemsList, insert, UPDATE_NAME, new StringValue(getNickName()));
        addColumnIfNotExists(existingColumns, itemsList, insert, UPDATE_TIME, timestampValue);
        addColumnIfNotExists(existingColumns, itemsList, insert, DELETED, new LongValue(0));

        insert.setItemsList(itemsList);
    }


    /**
     * 如果指定字段不存在于插入语句中，则添加该字段
     *
     * @param existingColumns 已存在的字段集合
     * @param itemsList       表达式列表
     * @param insert          插入语句对象
     * @param fieldName       字段名
     * @param fieldValue      字段值
     */
    private void addColumnIfNotExists(Set<String> existingColumns, ExpressionList itemsList, Insert insert, String fieldName, net.sf.jsqlparser.expression.Expression fieldValue) {
        if (!existingColumns.contains(fieldName)) {
            itemsList.addExpressions(fieldValue);
            insert.addColumns(new Column(null, fieldName));
        }
    }

    /**
     * 处理更新语句，在更新数据时自动填充更新人相关信息字段
     *
     * <p>该方法会在执行UPDATE语句时自动添加以下字段：
     * <ul>
     *   <li>update_by: 更新人ID</li>
     *   <li>update_name: 更新人姓名</li>
     *   <li>update_time: 更新时间</li>
     * </ul>
     *
     * @param update 待处理的更新语句对象
     * @param index  SQL语句索引
     * @param sql    原始SQL语句
     * @param obj    方法参数对象
     */
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        // 优化更新语句处理，避免重复获取用户信息和创建UpdateSet对象
        String userName = getUserName();
        String nickName = getNickName();
        TimestampValue timestampValue = new TimestampValue(LocalDateTime.now().format(FORMATTER));

        // 批量添加更新字段，减少重复代码
        List<UpdateSet> additionalUpdates = Arrays.asList(
                new UpdateSet(new Column(null, UPDATE_BY), new StringValue(userName)),
                new UpdateSet(new Column(null, UPDATE_NAME), new StringValue(nickName)),
                new UpdateSet(new Column(null, UPDATE_TIME), timestampValue)
        );

        update.getUpdateSets().addAll(additionalUpdates);
    }

    private void addMappedStatementCache(MappedStatement ms) {
        if (ContextHolder.getRewrite()) {
            return;
        }
    }


    /**
     * 获取当前登录用户的用户名
     *
     * @return 返回当前登录用户的用户名，如果未登录则返回空字符串
     */
    public String getUserName() {
        //测试
        return "admin";
        //TODO 修改好SecurityContextHolder后再改回来
        //return SecurityContextHolder.getUserName();
    }

    /**
     * 获取当前登录用户的昵称
     *
     * @return 返回当前登录用户的昵称，如果未登录则返回空字符串
     */
    public String getNickName() {
        //测试
        return "admin";
        //TODO 修改好SecurityContextHolder后再改回来
        //return SecurityContextHolder.getNickName();
    }

    public String getUserId() {
        Long userId = SecurityContextHolder.getUserId();
        return Objects.nonNull(userId) ? String.valueOf(userId) : "";
    }

    /**
     * 方便传递是否重写了sql
     *
     * @author yaolingjie
     */
    static final class ContextHolder {

        /**
         * SQL 是否进行重写
         */
        private static final ThreadLocal<Boolean> REWRITE = new TransmittableThreadLocal<>();

        public static void init() {
            REWRITE.set(false);
        }

        public static void clear() {
            REWRITE.remove();
        }

        public static boolean getRewrite() {
            return REWRITE.get();
        }


    }

    /**
     * {@link MappedStatement} 缓存
     * 针对无需改写的sql进行记录，可以避免 SQL 的解析，加快速度
     *
     * @author zhangzhixiang
     */
    static final class MappedStatementCache {

        /**
         * 记录无需重写的 {@link MappedStatement}
         */
        @Getter
        private final Set<String> noRewritable = new ConcurrentHashSet<>();

    }
}