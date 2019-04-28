/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.xss

import io.renren.common.exception.RRException
import org.apache.commons.lang.StringUtils

/**
 * SQL过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
object SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    fun sqlInject(str: String): String? {
        var input = str
        if (StringUtils.isBlank(input)) {
            return null
        }
        //去掉'|"|;|\字符
        input = StringUtils.replace(input, "'", "")
        input = StringUtils.replace(input, "\"", "")
        input = StringUtils.replace(input, ";", "")
        input = StringUtils.replace(input, "\\", "")

        //转换成小写
        input = input.toLowerCase()

        //非法字符
        val keywords = arrayOf("master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop")

        //判断是否包含非法字符
        for (keyword in keywords) {
            if (input.indexOf(keyword) != -1) {
                throw RRException("包含非法字符")
            }
        }

        return input
    }
}
