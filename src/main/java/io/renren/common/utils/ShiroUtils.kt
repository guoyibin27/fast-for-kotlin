/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils

import io.renren.common.exception.RRException
import io.renren.modules.sys.entity.SysUserEntity
import org.apache.shiro.SecurityUtils
import org.apache.shiro.session.Session
import org.apache.shiro.subject.Subject

/**
 * Shiro工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
object ShiroUtils {

    val session: Session
        get() = SecurityUtils.getSubject().session

    val subject: Subject
        get() = SecurityUtils.getSubject()

    val userEntity: SysUserEntity
        get() = SecurityUtils.getSubject().principal as SysUserEntity

    val userId: Long
        get() = userEntity.userId!!

    val isLogin: Boolean
        get() = SecurityUtils.getSubject().principal != null

    fun setSessionAttribute(key: Any, value: Any) {
        session.setAttribute(key, value)
    }

    fun getSessionAttribute(key: Any): Any? {
        return session.getAttribute(key)
    }

    fun getKaptcha(key: String): String {
        val kaptcha = getSessionAttribute(key) ?: throw RRException("验证码已失效")
        session.removeAttribute(key)
        return kaptcha.toString()
    }

}
