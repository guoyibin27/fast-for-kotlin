/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller

import io.renren.common.utils.R
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.form.SysLoginForm
import io.renren.modules.sys.service.SysCaptchaService
import io.renren.modules.sys.service.SysUserService
import io.renren.modules.sys.service.SysUserTokenService
import org.apache.commons.io.IOUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import javax.imageio.ImageIO
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse
import java.awt.image.BufferedImage
import java.io.IOException

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
class SysLoginController : AbstractController() {
    @Autowired
    lateinit var sysUserService: SysUserService
    @Autowired
    lateinit var sysUserTokenService: SysUserTokenService
    @Autowired
    lateinit var sysCaptchaService: SysCaptchaService

    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    @Throws(IOException::class)
    fun captcha(response: HttpServletResponse, uuid: String) {
        response.setHeader("Cache-Control", "no-store, no-cache")
        response.contentType = "image/jpeg"

        //获取图片验证码
        val image = sysCaptchaService.getCaptcha(uuid)

        val out = response.outputStream
        ImageIO.write(image, "jpg", out)
        IOUtils.closeQuietly(out)
    }

    /**
     * 登录
     */
    @PostMapping("/sys/login")
    @Throws(IOException::class)
    fun login(@RequestBody form: SysLoginForm): Map<String, Any> {
        val captcha = sysCaptchaService.validate(form.uuid!!, form.captcha!!)
        if (!captcha) {
            return R.error("验证码不正确")
        }

        //用户信息
        val user = sysUserService.queryByUserName(form.username!!)

        //账号不存在、密码错误
        if (user == null || !user.password.equals(Sha256Hash(form.password, user.salt).toHex())) {
            return R.error("账号或密码不正确")
        }

        //账号锁定
        if (user.status == 0) {
            return R.error("账号已被锁定,请联系管理员")
        }

        //生成token，并保存到数据库
        return sysUserTokenService.createToken(user.userId!!)
    }


    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    fun logout(): R {
        sysUserTokenService.logout(userId.toLong())
        return R.ok()
    }

}
