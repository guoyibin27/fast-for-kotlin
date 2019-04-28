/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.validator

import io.renren.common.exception.RRException

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author Mark sunlightcs@gmail.com
 */
object ValidatorUtils {
    private var validator: Validator? = null

    init {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws RRException  校验不通过，则报RRException异常
     */
    @Throws(RRException::class)
    fun validateEntity(`object`: Any, vararg groups: Class<*>) {
        val constraintViolations = validator!!.validate<Any>(`object`, *groups)
        if (!constraintViolations.isEmpty()) {
            val msg = StringBuilder()
            for (constraint in constraintViolations) {
                msg.append(constraint.message).append("<br>")
            }
            throw RRException(msg.toString())
        }
    }
}
