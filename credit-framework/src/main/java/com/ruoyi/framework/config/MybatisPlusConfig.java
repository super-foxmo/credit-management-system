package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ruoyi.system.convert.JsonTypeHandler;
import com.ruoyi.system.convert.ScoreRuleJsonToListHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class MybatisPlusConfig {

    public SqlSessionFactory sqlSessionFactory() {
        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.getTypeHandlerRegistry().register(JsonTypeHandler.class);
        configuration.getTypeHandlerRegistry().register(ScoreRuleJsonToListHandler.class);

        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//如果配置多个插件,切记分页最后添加
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        return interceptor;
    }
}