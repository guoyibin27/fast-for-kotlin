/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.renren.modules.sys.entity.SysRoleEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
interface SysRoleDao : BaseMapper<SysRoleEntity> {

    /**
     * 查询用户创建的角色ID列表
     */
    fun queryRoleIdList(createUserId: Long): List<Long>
}
