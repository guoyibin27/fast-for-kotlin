/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2

import io.renren.common.exception.RRException

import java.security.MessageDigest
import java.util.UUID
import kotlin.experimental.and

/**
 * 生成token
 *
 * @author Mark sunlightcs@gmail.com
 */
object TokenGenerator {

    private val hexCode = "0123456789abcdef".toCharArray()

    fun toHexString(data: ByteArray?): String? {
        if (data == null) {
            return null
        }
        val r = StringBuilder(data.size * 2)
        for (b in data) {
            r.append(hexCode[b.toInt() shr 4 and 0xF])
            r.append(hexCode[b.toInt() and 0xF])
        }
        return r.toString()
    }

    fun generateValue(param: String = UUID.randomUUID().toString()): String? {
        try {
            val algorithm = MessageDigest.getInstance("MD5")
            algorithm.reset()
            algorithm.update(param.toByteArray())
            val messageDigest = algorithm.digest()
            return toHexString(messageDigest)
        } catch (e: Exception) {
            throw RRException("生成Token失败", e)
        }
    }
}
