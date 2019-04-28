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
import io.renren.common.validator.ValidatorUtils
import io.renren.modules.sys.entity.SysRoleEntity
import io.renren.modules.sys.service.SysRoleMenuService
import io.renren.modules.sys.service.SysRoleService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import java.util.HashMap

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
class SysRoleController : AbstractController() {
    @Autowired
    lateinit var sysRoleService: SysRoleService
    @Autowired
    lateinit var sysRoleMenuService: SysRoleMenuService

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    fun list(@RequestParam params: MutableMap<String, Any>): R {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (userId != Constant.SUPER_ADMIN) {
            params.put("createUserId", userId)
        }

        val page = sysRoleService.queryPage(params)

        return R.ok().put("page", page)
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    fun select(): R {
        val map = HashMap<String, Any>()

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (userId != Constant.SUPER_ADMIN) {
            map.put("create_user_id", userId)
        }
        val list = sysRoleService.listByMap(map)

        return R.ok().put("list", list)
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    fun info(@PathVariable("roleId") roleId: Long): R {
        val role = sysRoleService.getById(roleId)

        //查询角色对应的菜单
        val menuIdList = sysRoleMenuService.queryMenuIdList(roleId)
        role.menuIdList = menuIdList

        return R.ok().put("role", role)
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    fun save(@RequestBody role: SysRoleEntity): R {
        ValidatorUtils.validateEntity(role)

        role.createUserId = userId
        sysRoleService.saveRole(role)

        return R.ok()
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    fun update(@RequestBody role: SysRoleEntity): R {
        ValidatorUtils.validateEntity(role)

        role.createUserId = userId
        sysRoleService.update(role)

        return R.ok()
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    fun delete(@RequestBody roleIds: Array<Long>): R {
        sysRoleService.deleteBatch(roleIds)

        return R.ok()
    }
}
