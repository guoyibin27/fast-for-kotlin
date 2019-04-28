/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.exception

import io.renren.common.utils.R
import org.apache.shiro.authz.AuthorizationException
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestControllerAdvice
class RRExceptionHandler {
    private val logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException::class)
    fun handleRRException(e: RRException): R {
        val r = R()
        r["code"] = e.code
        r["msg"] = e.message ?: ""

        return r
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handlerNoFoundException(e: Exception): R {
        logger.error(e.message ?: "", e)
        return R.error(404, "路径不存在，请检查路径是否正确")
    }

    @ExceptionHandler(DuplicateKeyException::class)
    fun handleDuplicateKeyException(e: DuplicateKeyException): R {
        logger.error(e.message, e)
        return R.error("数据库中已存在该记录")
    }

    @ExceptionHandler(AuthorizationException::class)
    fun handleAuthorizationException(e: AuthorizationException): R {
        logger.error(e.message ?: "", e)
        return R.error("没有权限，请联系管理员授权")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): R {
        logger.error(e.message ?: "", e)
        return R.error()
    }
}
