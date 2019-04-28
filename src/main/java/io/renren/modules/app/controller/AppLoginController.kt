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
import io.renren.modules.app.form.LoginForm
import io.renren.modules.app.service.UserService
import io.renren.modules.app.utils.JwtUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.HashMap

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
class AppLoginController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var jwtUtils: JwtUtils

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    fun login(@RequestBody form: LoginForm): R {
        //表单校验
        ValidatorUtils.validateEntity(form)
        //用户登录
        val userId = userService.login(form)
        //生成token
        val token = jwtUtils.generateToken(userId)
        val map = HashMap<String, Any>()
        map["token"] = token
        map["expire"] = jwtUtils.expire
        return R.ok(map)
    }

}
