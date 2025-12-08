package com.whm.common.mybatis.plugins;


import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.whm.common.core.context.SecurityContextHolder;
import com.whm.common.mybatis.config.IgnoreTableConfig;
import lombok.Getter;
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
 * 创建人信息
 *
 * @author : 吴华明
 * @since 2025-11-27  19:41
 */
public class CommonFieldInterceptor extends JsqlParserSupport implements InnerInterceptor {

    //    @Autowired
    private IgnoreTableConfig ignoreTableConfig;

    final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private static final String CREATE_BY = "create_by";
    private static final String CREATE_NAME = "create_name";
    private static final String CREATE_TIME = "create_time";
    private static final String UPDATE_BY = "update_by";
    private static final String UPDATE_NAME = "update_name";
    private static final String UPDATE_TIME = "update_time";
    private static final String DELETED = "deleted";


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
                // log.error("JsqlParser 解析 SQL 语句异常:", e);
                // 处理解析异常
                return;
            }

            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List<String> tableNames = tablesNamesFinder.getTableList(statement);
//            List<String> tables = ignoreTableConfig.getTables();
            List<String> tables = Arrays.asList("media_files", "media_process_history");
            // 在这里可以获取到当前 SQL 语句中涉及的表名
            for (String tableName : tableNames) {
                if (tables.contains(tableName)) {
                    return;
                }
                //如果是**结尾,则进行模糊匹配
                if (tables.stream().filter(m -> m.endsWith("**")).anyMatch(m -> tableName.startsWith(StringUtils.removeEnd(m, "**")))) {
                    return;
                }
            }

            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            try {
                // 初始化上下文
                ContextHolder.init();
                // 检查是否需要处理
                // 处理 SQL
                mpBs.sql(parserMulti(mpBs.sql(), null));
            } finally {
                addMappedStatementCache(ms);
                ContextHolder.clear();
            }
        }
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        if (!(insert.getItemsList() instanceof ExpressionList)) {
            return;
        }
        ExpressionList itemsList = insert.getItemsList(ExpressionList.class);

        // 获取插入语句中的列列表
        List<Column> columns = insert.getColumns();

        Set<String> columnName = new HashSet<>();
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (CREATE_BY.equals(column.getColumnName())) {
                columnName.add(CREATE_BY);
            }
            if (CREATE_NAME.equals(column.getColumnName())) {
                columnName.add(CREATE_NAME);
            }
            if (CREATE_TIME.equals(column.getColumnName())) {
                columnName.add(CREATE_TIME);
            }
            if (UPDATE_BY.equals(column.getColumnName())) {
                columnName.add(UPDATE_BY);
            }
            if (UPDATE_NAME.equals(column.getColumnName())) {
                columnName.add(UPDATE_NAME);
            }
            if (UPDATE_TIME.equals(column.getColumnName())) {
                columnName.add(UPDATE_TIME);
            }
            if (DELETED.equals(column.getColumnName())) {
                columnName.add(DELETED);
            }
        }

        TimestampValue timestampValue = new TimestampValue(LocalDateTime.now().format(FORMATTER));
        if (!columnName.contains(CREATE_BY)) {
            itemsList.addExpressions(new StringValue(getUserName()));
            insert.addColumns(new Column(null, CREATE_BY));
        }

        if (!columnName.contains(CREATE_NAME)) {
            itemsList.addExpressions(new StringValue(getNickName()));
            insert.addColumns(new Column(null, CREATE_NAME));
        }

        if (!columnName.contains(CREATE_TIME)) {
            itemsList.addExpressions(timestampValue);
            insert.addColumns(new Column(null, CREATE_TIME));
        }

        if (!columnName.contains(UPDATE_BY)) {
            itemsList.addExpressions(new StringValue(getUserName()));
            insert.addColumns(new Column(null, UPDATE_BY));
        }

        if (!columnName.contains(UPDATE_NAME)) {
            itemsList.addExpressions(new StringValue(getNickName()));
            insert.addColumns(new Column(null, UPDATE_NAME));
        }

        if (!columnName.contains(UPDATE_TIME)) {
            itemsList.addExpressions(timestampValue);
            insert.addColumns(new Column(null, UPDATE_TIME));
        }

        if (!columnName.contains(DELETED)) {
            itemsList.addExpressions(new LongValue(0));
            insert.addColumns(new Column(null, DELETED));
        }

        insert.setItemsList(itemsList);
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {

        Column updateByColumn = new Column(null, UPDATE_BY);
        ArrayList<UpdateSet> updateSets = update.getUpdateSets();
        updateSets.add(new UpdateSet(updateByColumn, new StringValue(getUserName())));

        Column updateByColumn2 = new Column(null, UPDATE_NAME);
        ArrayList<UpdateSet> updateSets2 = update.getUpdateSets();
        updateSets2.add(new UpdateSet(updateByColumn2, new StringValue(getNickName())));

        TimestampValue timestampValue = new TimestampValue(LocalDateTime.now().format(FORMATTER));

        Column updateByColumn3 = new Column(null, UPDATE_TIME);
        ArrayList<UpdateSet> updateSets3 = update.getUpdateSets();
        updateSets3.add(new UpdateSet(updateByColumn3, timestampValue));


    }

    private void addMappedStatementCache(MappedStatement ms) {
        if (ContextHolder.getRewrite()) {
            return;
        }
    }


    public String getUserName() {
        return SecurityContextHolder.getUserName();
    }

    public String getNickName() {
        return SecurityContextHolder.getNickName();
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