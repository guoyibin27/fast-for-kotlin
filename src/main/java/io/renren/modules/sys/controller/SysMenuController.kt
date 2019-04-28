/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller

import io.renren.common.annotation.SysLog
import io.renren.common.exception.RRException
import io.renren.common.utils.Constant
import io.renren.common.utils.R
import io.renren.modules.sys.entity.SysMenuEntity
import io.renren.modules.sys.service.ShiroService
import io.renren.modules.sys.service.SysMenuService
import org.apache.commons.lang.StringUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/menu")
class SysMenuController : AbstractController() {
    @Autowired
    lateinit var sysMenuService: SysMenuService
    @Autowired
    lateinit var shiroService: ShiroService

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    fun nav(): R {
        val menuList = sysMenuService.getUserMenuList(userId)
        val permissions = shiroService.getUserPermissions(userId)
        return R.ok().put("menuList", menuList).put("permissions", permissions)
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    fun list(): List<SysMenuEntity> {
        val menuList = sysMenuService.list()
        for (sysMenuEntity in menuList) {
            val parentMenuEntity = sysMenuService.getById(sysMenuEntity.parentId)
            if (parentMenuEntity != null) {
                sysMenuEntity.parentName = parentMenuEntity.name
            }
        }

        return menuList
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    fun select(): R {
        //查询列表数据
        val menuList = sysMenuService.queryNotButtonList().toMutableList()

        //添加顶级菜单
        val root = SysMenuEntity()
        root.menuId = 0L
        root.name = "一级菜单"
        root.parentId = -1L
        root.open = true
        menuList.add(root)

        return R.ok().put("menuList", menuList)
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    fun info(@PathVariable("menuId") menuId: Long): R {
        val menu = sysMenuService.getById(menuId)
        return R.ok().put("menu", menu)
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    fun save(@RequestBody menu: SysMenuEntity): R {
        //数据校验
        verifyForm(menu)

        sysMenuService.save(menu)

        return R.ok()
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    fun update(@RequestBody menu: SysMenuEntity): R {
        //数据校验
        verifyForm(menu)

        sysMenuService.updateById(menu)

        return R.ok()
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    fun delete(@PathVariable("menuId") menuId: Long): R {
        if (menuId <= 31) {
            return R.error("系统菜单，不能删除")
        }

        //判断是否有子菜单或按钮
        val menuList = sysMenuService.queryListParentId(menuId)
        if (menuList.count() > 0) {
            return R.error("请先删除子菜单或按钮")
        }

        sysMenuService.delete(menuId)

        return R.ok()
    }

    /**
     * 验证参数是否正确
     */
    private fun verifyForm(menu: SysMenuEntity) {
        if (menu.name.isNullOrEmpty()) {
            throw RRException("菜单名称不能为空")
        }

        if (menu.parentId != null) {
            throw RRException("上级菜单不能为空")
        }

        //菜单
        if (menu.type == Constant.MenuType.MENU.value) {
            if (StringUtils.isBlank(menu.url)) {
                throw RRException("菜单URL不能为空")
            }
        }

        //上级菜单类型
        var parentType = Constant.MenuType.CATALOG.value
        if (menu.parentId != 0L) {
            val parentMenu = sysMenuService.getById(menu.parentId)
            parentType = parentMenu.type!!
        }

        //目录、菜单
        if (menu.type == Constant.MenuType.CATALOG.value || menu.type == Constant.MenuType.MENU.value) {
            if (parentType != Constant.MenuType.CATALOG.value) {
                throw RRException("上级菜单只能为目录类型")
            }
            return
        }

        //按钮
        if (menu.type == Constant.MenuType.BUTTON.value) {
            if (parentType != Constant.MenuType.MENU.value) {
                throw RRException("上级菜单只能为菜单类型")
            }
            return
        }
    }
}
