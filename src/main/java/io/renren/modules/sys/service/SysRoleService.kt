/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service

import com.baomidou.mybatisplus.extension.service.IService
import io.renren.common.utils.PageUtils
import io.renren.modules.sys.entity.SysRoleEntity
import oracle.sql.Mutable


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysRoleService : IService<SysRoleEntity> {

    fun queryPage(params: MutableMap<String, Any>): PageUtils

    fun saveRole(role: SysRoleEntity)

    fun update(role: SysRoleEntity)

    fun deleteBatch(roleIds: Array<Long>)


    /**
     * 查询用户创建的角色ID列表
     */
    fun queryRoleIdList(createUserId: Long): List<Long>
}
