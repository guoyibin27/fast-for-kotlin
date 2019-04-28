/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.utils.Constant
import io.renren.common.utils.MapUtils
import io.renren.modules.sys.dao.SysMenuDao
import io.renren.modules.sys.entity.SysMenuEntity
import io.renren.modules.sys.service.SysMenuService
import io.renren.modules.sys.service.SysRoleMenuService
import io.renren.modules.sys.service.SysUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.ArrayList


@Service("sysMenuService")
open class SysMenuServiceImpl : ServiceImpl<SysMenuDao, SysMenuEntity>(), SysMenuService {

    @Autowired
    lateinit var sysUserService: SysUserService
    @Autowired
    lateinit var sysRoleMenuService: SysRoleMenuService

    override fun queryListParentId(parentId: Long, menuIdList: List<Long>?): List<SysMenuEntity> {
        val menuList = baseMapper.queryListParentId(parentId)
        if (menuIdList == null) {
            return emptyList()
        }
        return menuList.filter { !menuIdList.contains(it.menuId) }
    }

    @Override
    override fun queryNotButtonList(): List<SysMenuEntity> {
        return baseMapper.queryNotButtonList()
    }

    @Override
    override fun getUserMenuList(userId: Long): List<SysMenuEntity> {
        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            return getAllMenuList(null)
        }

        //用户菜单列表
        val menuIdList = sysUserService.queryAllMenuId(userId)
        return getAllMenuList(menuIdList)
    }

    @Override
    override fun delete(menuId: Long) {
        //删除菜单
        this.removeById(menuId)
        //删除菜单与角色关联
        sysRoleMenuService.removeByMap(MapUtils().put("menu_id", menuId))
    }

    /**
     * 获取所有菜单列表
     */
    private fun getAllMenuList(menuIdList: List<Long>?): List<SysMenuEntity> {
        //查询根菜单列表
        val menuList = queryListParentId(0L, menuIdList)
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList)

        return menuList
    }

    /**
     * 递归
     */
    private fun getMenuTreeList(menuList: List<SysMenuEntity>, menuIdList: List<Long>?): List<SysMenuEntity> {
        val subMenuList = ArrayList<SysMenuEntity>()

        for (entity in menuList) {
            //目录
            if (entity.type == Constant.MenuType.CATALOG.value) {
                entity.list = getMenuTreeList(queryListParentId(entity.menuId!!, menuIdList), menuIdList)
            }
            subMenuList.add(entity)
        }

        return subMenuList
    }
}
