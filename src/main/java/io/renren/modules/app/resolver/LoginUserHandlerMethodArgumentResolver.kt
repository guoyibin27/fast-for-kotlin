/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.resolver

import io.renren.modules.app.annotation.LoginUser
import io.renren.modules.app.entity.UserEntity
import io.renren.modules.app.interceptor.AuthorizationInterceptor
import io.renren.modules.app.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
class LoginUserHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    @Autowired
    lateinit var userService: UserService


    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType.isAssignableFrom(UserEntity::class.java) && parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    @Override
    @Throws(Exception::class)
    public override fun resolveArgument(parameter: MethodParameter, container: ModelAndViewContainer?,
                                        request: NativeWebRequest, factory: WebDataBinderFactory?): Any? {
        //获取用户ID
        val userId = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST)
                ?: return null

        //获取用户信
        return userService.getById(userId.toString().toLong())
    }
}
