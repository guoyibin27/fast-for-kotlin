/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service

import com.baomidou.mybatisplus.extension.service.IService
import io.renren.modules.sys.entity.SysUserRoleEntity


/**
 * 用户与角色对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysUserRoleService : IService<SysUserRoleEntity> {

    fun saveOrUpdate(userId: Long, roleIdList: List<Long>?)

    /**
     * 根据用户ID，获取角色ID列表
     */
    fun queryRoleIdList(userId: Long): List<Long>

    /**
     * 根据角色ID数组，批量删除
     */
    fun deleteBatch(roleIds: Array<Long>): Int
}
