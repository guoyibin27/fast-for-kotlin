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
import io.renren.modules.sys.entity.SysUserEntity


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysUserService : IService<SysUserEntity> {

    fun queryPage(params: MutableMap<String, Any>): PageUtils

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

    /**
     * 保存用户
     */
    fun saveUser(user: SysUserEntity)

    /**
     * 修改用户
     */
    fun update(user: SysUserEntity)

    /**
     * 删除用户
     */
    fun deleteBatch(userIds: Array<Long>)

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    fun updatePassword(userId: Long, password: String, newPassword: String): Boolean
}
