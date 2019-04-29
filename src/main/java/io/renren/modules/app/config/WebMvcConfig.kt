/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.config

import io.renren.modules.app.interceptor.AuthorizationInterceptor
import io.renren.modules.app.resolver.LoginUserHandlerMethodArgumentResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * MVC配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
 class WebMvcConfig : WebMvcConfigurer {
    @Autowired
    lateinit var authorizationInterceptor: AuthorizationInterceptor
    @Autowired
    lateinit var loginUserHandlerMethodArgumentResolver: LoginUserHandlerMethodArgumentResolver

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/app/**")
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver)
    }
}