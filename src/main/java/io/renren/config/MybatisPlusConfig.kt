/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.config

import com.baomidou.mybatisplus.core.injector.ISqlInjector
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * mybatis-plus配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
 class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
     fun paginationInterceptor(): PaginationInterceptor {
        return PaginationInterceptor()
    }

    @Bean
     fun sqlInjector(): ISqlInjector {
        return LogicSqlInjector()
    }
}
