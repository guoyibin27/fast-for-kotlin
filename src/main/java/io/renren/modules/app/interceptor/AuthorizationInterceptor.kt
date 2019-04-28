/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.interceptor


import io.jsonwebtoken.Claims
import io.renren.common.exception.RRException
import io.renren.modules.app.utils.JwtUtils
import io.renren.modules.app.annotation.Login
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 权限(Token)验证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
class AuthorizationInterceptor : HandlerInterceptorAdapter() {
    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Override
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val annotation: Login?
        if (handler is HandlerMethod) {
            annotation = handler.getMethodAnnotation(Login::class.java)
        } else {
            return true
        }

        if (annotation == null) {
            return true
        }

        //获取用户凭证
        var token = request.getHeader(jwtUtils.header)
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(jwtUtils.header)
        }

        //凭证为空
        if (StringUtils.isBlank(token)) {
            throw RRException(jwtUtils.header!!.toString() + "不能为空", HttpStatus.UNAUTHORIZED.value())
        }

        val claims = jwtUtils.getClaimByToken(token)
        if (claims == null || jwtUtils.isTokenExpired(claims.expiration)) {
            throw RRException(jwtUtils.header!!.toString() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value())
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, claims.subject.toLong())

        return true
    }

    companion object {

        val USER_KEY = "userId"
    }
}
