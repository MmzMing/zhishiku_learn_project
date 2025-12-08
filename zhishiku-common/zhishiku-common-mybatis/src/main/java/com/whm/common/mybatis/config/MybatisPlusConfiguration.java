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
        // TODO 后面再研究如何添加公共字段
        //interceptor.addInnerInterceptor(commonFieldInterceptor());
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

    @Bean
    public CommonFieldInterceptor commonFieldInterceptor() {
        return new CommonFieldInterceptor();
    }
}