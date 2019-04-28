/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service


import com.baomidou.mybatisplus.extension.service.IService
import io.renren.modules.sys.entity.SysMenuEntity


/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysMenuService : IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    fun queryListParentId(parentId: Long, menuIdList: List<Long>? = null): List<SysMenuEntity>

    /**
     * 获取不包含按钮的菜单列表
     */
    fun queryNotButtonList(): List<SysMenuEntity>

    /**
     * 获取用户菜单列表
     */
    fun getUserMenuList(userId: Long): List<SysMenuEntity>

    /**
     * 删除
     */
    fun delete(menuId: Long)
}
