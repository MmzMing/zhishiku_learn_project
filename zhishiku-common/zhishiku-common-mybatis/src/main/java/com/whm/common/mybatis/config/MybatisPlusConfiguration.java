package com.whm.common.mybatis.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.whm.common.mybatis.plugins.CommonFieldInterceptor;
import com.whm.common.mybatis.plugins.SqlLogInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MYbatisPlus配置类
 *
 * @author 吴华明
 * @date 2025/9/6 13:10
 */
@Configuration
@MapperScan("com.whm.**.mapper")
public class MybatisPlusConfiguration {

    /**
     * 新增分页拦截器，并设置数据库类型为mysql
     */
    @Bean(name = "mybatisPlusInterceptor")
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        //分页单页最大条数
        paginationInnerInterceptor.setMaxLimit(5000L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(commonFieldInterceptor());
        //添加sql日志拦截器
        return interceptor;
    }

    /**
     * sql 日志
     *
     * @return SqlLogInterceptor
     */
    @Bean
    @ConditionalOnProperty(value = "mybatis-plus.sql-log.enable", matchIfMissing = true)
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

    /**
     * 创建并配置公共字段拦截器实例
     * 
     * <p>该拦截器用于在执行INSERT和UPDATE操作时自动填充以下公共字段：
     * create_by、create_name、create_time、update_by、update_name、update_time、deleted
     *
     * @return CommonFieldInterceptor 公共字段拦截器实例
     */
    @Bean
    public CommonFieldInterceptor commonFieldInterceptor() {
        return new CommonFieldInterceptor();
    }
}