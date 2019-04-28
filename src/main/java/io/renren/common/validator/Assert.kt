/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.validator

import io.renren.common.exception.RRException
import org.apache.commons.lang.StringUtils

/**
 * 数据校验
 *
 * @author Mark sunlightcs@gmail.com
 */
object Assert {

    fun isBlank(str: String?, message: String) {
        if (str.isNullOrEmpty()) {
            throw RRException(message)
        }
    }

    fun isNull(`object`: Any?, message: String) {
        if (`object` == null) {
            throw RRException(message)
        }
    }
}
