/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletRequest

object HttpContextUtils {

    val httpServletRequest: HttpServletRequest
        get() = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

    val domain: String
        get() {
            val request = httpServletRequest
            val url = request.requestURL
            return url.delete(url.length - request.requestURI.length, url.length).toString()
        }

    val origin: String
        get() {
            val request = httpServletRequest
            return request.getHeader("Origin")
        }
}
