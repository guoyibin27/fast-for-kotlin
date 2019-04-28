/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils

/**
 * Redis所有Keys
 *
 * @author Mark sunlightcs@gmail.com
 */
object RedisKeys {

    fun getSysConfigKey(key: String): String {
        return "sys:config:$key"
    }
}
