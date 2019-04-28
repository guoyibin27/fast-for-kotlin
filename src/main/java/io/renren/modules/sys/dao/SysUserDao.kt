/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.renren.modules.sys.entity.SysUserEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
interface SysUserDao : BaseMapper<SysUserEntity> {

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    fun queryAllPerms(userId: Long): List<String>

    /**
     * 查询用户的所有菜单ID
     */
    fun queryAllMenuId(userId: Long): List<Long>

    /**
     * 根据用户名，查询系统用户
     */
    fun queryByUserName(username: String): SysUserEntity?

}
