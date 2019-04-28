/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils

import org.apache.http.HttpStatus

import java.util.HashMap

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
class R : HashMap<String, Any>() {
    init {
        put("code", 0)
        put("msg", "success")
    }

    override fun put(key: String, value: Any): R {
        super.put(key, value)
        return this
    }

    companion object {
        fun error(msg: String): R {
            return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg)
        }

        @JvmOverloads
        fun error(code: Int = HttpStatus.SC_INTERNAL_SERVER_ERROR, msg: String = "未知异常，请联系管理员"): R {
            val r = R()
            r["code"] = code
            r["msg"] = msg
            return r
        }

        fun ok(msg: String): R {
            val r = R()
            r["msg"] = msg
            return r
        }

        fun ok(map: Map<String, Any>): R {
            val r = R()
            r.putAll(map)
            return r
        }

        fun ok(): R {
            return R()
        }
    }
}
