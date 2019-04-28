/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.renren.modules.sys.entity.SysRoleMenuEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
interface SysRoleMenuDao : BaseMapper<SysRoleMenuEntity> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    fun queryMenuIdList(roleId: Long): List<Long>

    /**
     * 根据角色ID数组，批量删除
     */
    fun deleteBatch(roleIds: Array<Long>): Int
}
