/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2

import com.google.gson.Gson
import io.renren.common.utils.HttpContextUtils
import io.renren.common.utils.R
import org.apache.commons.lang.StringUtils
import org.apache.http.HttpStatus
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.web.filter.authc.AuthenticatingFilter
import org.springframework.web.bind.annotation.RequestMethod

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

/**
 * oauth2过滤器
 *
 * @author Mark sunlightcs@gmail.com
 */
class OAuth2Filter : AuthenticatingFilter() {

    @Throws(Exception::class)
    override fun createToken(request: ServletRequest, response: ServletResponse): AuthenticationToken? {
        //获取请求token
        val token = getRequestToken(request as HttpServletRequest)

        return if (StringUtils.isBlank(token)) {
            null
        } else OAuth2Token(token)

    }


    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any): Boolean {
        return (request as HttpServletRequest).method.equals(RequestMethod.OPTIONS.name)

    }

    @Override
    @Throws(Exception::class)
    override fun onAccessDenied(request: ServletRequest, response: ServletResponse): Boolean {
        //获取请求token，如果token不存在，直接返回401
        val token = getRequestToken(request as HttpServletRequest)
        if (StringUtils.isBlank(token)) {
            val httpResponse = response as HttpServletResponse
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true")
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.origin)

            val json = Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"))

            httpResponse.writer.print(json)

            return false
        }

        return executeLogin(request, response)
    }

    @Override
    override fun onLoginFailure(token: AuthenticationToken?, e: AuthenticationException, request: ServletRequest?, response: ServletResponse?): Boolean {
        val httpResponse = response as HttpServletResponse?
        httpResponse!!.contentType = "application/json;charset=utf-8"
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true")
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.origin)
        try {
            //处理登录失败的异常
            val throwable = if (e.cause == null) e else e.cause!!
            val r = R.error(HttpStatus.SC_UNAUTHORIZED, throwable.message ?: "")

            val json = Gson().toJson(r)
            httpResponse.writer.print(json)
        } catch (e1: IOException) {

        }

        return false
    }

    /**
     * 获取请求的token
     */
    private fun getRequestToken(httpRequest: HttpServletRequest): String {
        //从header中获取token
        var token = httpRequest.getHeader("token")

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token")
        }

        return token
    }


}
