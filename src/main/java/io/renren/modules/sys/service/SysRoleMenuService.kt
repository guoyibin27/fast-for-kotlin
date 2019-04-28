/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service

import com.baomidou.mybatisplus.extension.service.IService
import io.renren.modules.sys.entity.SysRoleMenuEntity


/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysRoleMenuService : IService<SysRoleMenuEntity> {

    fun saveOrUpdate(roleId: Long, menuIdList: List<Long>)

    /**
     * 根据角色ID，获取菜单ID列表
     */
    fun queryMenuIdList(roleId: Long): List<Long>

    /**
     * 根据角色ID数组，批量删除
     */
    fun deleteBatch(roleIds: Array<Long>): Int

}
