/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.xss

import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.Charset
import java.util.LinkedHashMap

/**
 * XSS过滤处理
 *
 * @author Mark sunlightcs@gmail.com
 */
class XssHttpServletRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    //没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
    /**
     * 获取最原始的request
     */
    var orgRequest: HttpServletRequest
        internal set

    init {
        orgRequest = request
    }

    @Override
    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        //非json类型，直接返回
        if (!MediaType.APPLICATION_JSON_VALUE.equals(super.getHeader(HttpHeaders.CONTENT_TYPE), true)) {
            return super.getInputStream()
        }

        //为空，直接返回
        var json = IOUtils.toString(super.getInputStream(), "utf-8")
        if (StringUtils.isBlank(json)) {
            return super.getInputStream()
        }

        //xss过滤
        json = xssEncode(json)
        val bis = ByteArrayInputStream(json.toByteArray(Charset.forName("utf-8")))
        return object : ServletInputStream() {
            override fun isFinished(): Boolean {
                return true
            }

            override fun isReady(): Boolean {
                return true
            }

            override fun setReadListener(readListener: ReadListener) {

            }

            @Throws(IOException::class)
            override fun read(): Int {
                return bis.read()
            }
        }
    }

    @Override
    override fun getParameter(name: String): String {
        var value = super.getParameter(xssEncode(name))
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value)
        }
        return value
    }

    @Override
    override fun getParameterValues(name: String): Array<String>? {
        val parameters = super.getParameterValues(name)
        if (parameters == null || parameters.size == 0) {
            return null
        }

        for (i in parameters.indices) {
            parameters[i] = xssEncode(parameters[i])
        }
        return parameters
    }

    @Override
    override fun getParameterMap(): Map<String, Array<String>> {
        val map = LinkedHashMap<String, Array<String>>()
        val parameters = super.getParameterMap()
        for (key in parameters.keys) {
            val values = parameters[key]
            for (i in values!!.indices) {
                values[i] = xssEncode(values[i])
            }
            map[key] = values
        }
        return map
    }

    @Override
    override fun getHeader(name: String): String {
        var value = super.getHeader(xssEncode(name))
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value)
        }
        return value
    }

    private fun xssEncode(input: String): String {
        return htmlFilter.filter(input)
    }

    companion object {
        //html过滤
        private val htmlFilter = HTMLFilter()

        /**
         * 获取最原始的request
         */
        fun getOrgRequest(request: HttpServletRequest): HttpServletRequest {
            return (request as? XssHttpServletRequestWrapper)?.orgRequest ?: request

        }
    }

}
