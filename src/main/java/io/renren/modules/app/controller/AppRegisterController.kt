/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller


import io.renren.common.utils.R
import io.renren.common.validator.ValidatorUtils
import io.renren.modules.app.entity.UserEntity
import io.renren.modules.app.form.RegisterForm
import io.renren.modules.app.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.Date

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP注册接口")
class AppRegisterController {
    @Autowired
    private val userService: UserService? = null

    @PostMapping("register")
    @ApiOperation("注册")
    fun register(@RequestBody form: RegisterForm): R {
        //表单校验
        ValidatorUtils.validateEntity(form)

        val user = UserEntity()
        user.mobile = form.mobile
        user.username = form.mobile
        user.password = DigestUtils.sha256Hex(form.password)
        user.createTime = Date()
        userService!!.save(user)

        return R.ok()
    }
}
