/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import java.util.Date

/**
 * jwt工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@ConfigurationProperties(prefix = "renren.jwt")
@Component
class JwtUtils {
    private val logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    var secret: String? = ""
    var expire: Long = 0
    var header: String? = null

    /**
     * 生成jwt token
     */
    fun generateToken(userId: Long): String {
        val nowDate = Date()
        //过期时间
        val expireDate = Date(nowDate.time + expire * 1000)

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId.toString() + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun getClaimByToken(token: String): Claims? {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
        } catch (e: Exception) {
            logger.debug("validate is token error ", e)
            return null
        }

    }

    /**
     * token是否过期
     * @return  true：过期
     */
    fun isTokenExpired(expiration: Date): Boolean {
        return expiration.before(Date())
    }
}
