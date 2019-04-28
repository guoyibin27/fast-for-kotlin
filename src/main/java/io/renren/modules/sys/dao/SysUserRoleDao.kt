/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.renren.modules.sys.entity.SysUserRoleEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 用户与角色对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
interface SysUserRoleDao : BaseMapper<SysUserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    fun queryRoleIdList(userId: Long): List<Long>


    /**
     * 根据角色ID数组，批量删除
     */
    fun deleteBatch(roleIds: Array<Long>): Int
}
