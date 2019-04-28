/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller

import io.renren.common.annotation.SysLog
import io.renren.common.utils.Constant
import io.renren.common.utils.PageUtils
import io.renren.common.utils.R
import io.renren.common.validator.Assert
import io.renren.common.validator.ValidatorUtils
import io.renren.common.validator.group.AddGroup
import io.renren.common.validator.group.UpdateGroup
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.form.PasswordForm
import io.renren.modules.sys.service.SysUserRoleService
import io.renren.modules.sys.service.SysUserService
import org.apache.commons.lang.ArrayUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
class SysUserController : AbstractController() {
    @Autowired
    lateinit var sysUserService: SysUserService
    @Autowired
    lateinit var sysUserRoleService: SysUserRoleService


    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    fun list(@RequestParam params: MutableMap<String, Any>): R {
        //只有超级管理员，才能查看所有管理员列表
        if (userId != Constant.SUPER_ADMIN) {
            params.put("createUserId", userId)
        }
        val page = sysUserService.queryPage(params)

        return R.ok().put("page", page)
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    fun info(): R {
        return R.ok().put("user", user)
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    fun password(@RequestBody form: PasswordForm): R {
        Assert.isBlank(form.newPassword, "新密码不为能空")

        //sha256加密
        val password = Sha256Hash(form.password!!, user.salt).toHex()
        //sha256加密
        val newPassword = Sha256Hash(form.newPassword, user.salt).toHex()

        //更新密码
        val flag = sysUserService.updatePassword(userId, password, newPassword)
        return if (!flag) {
            R.error("原密码不正确")
        } else R.ok()

    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    fun info(@PathVariable("userId") userId: Long): R {
        val user = sysUserService.getById(userId)

        //获取用户所属的角色列表
        val roleIdList = sysUserRoleService.queryRoleIdList(userId)
        user.roleIdList = roleIdList

        return R.ok().put("user", user)
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    fun save(@RequestBody user: SysUserEntity): R {
        ValidatorUtils.validateEntity(user, AddGroup::class.java)

        user.createUserId = userId
        sysUserService.saveUser(user)

        return R.ok()
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    fun update(@RequestBody user: SysUserEntity): R {
        ValidatorUtils.validateEntity(user, UpdateGroup::class.java)

        user.createUserId = userId
        sysUserService.update(user)

        return R.ok()
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    fun delete(@RequestBody userIds: Array<Long>): R {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除")
        }

        if (ArrayUtils.contains(userIds, userId)) {
            return R.error("当前用户不能删除")
        }

        sysUserService.deleteBatch(userIds)

        return R.ok()
    }
}
