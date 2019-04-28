/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service

import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.entity.SysUserTokenEntity

/**
 * shiro相关接口
 *
 * @author Mark sunlightcs@gmail.com
 */
interface ShiroService {
    /**
     * 获取用户权限列表
     */
    fun getUserPermissions(userId: Long): Set<String>

    fun queryByToken(token: String): SysUserTokenEntity?

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    fun queryUser(userId: Long): SysUserEntity?
}
