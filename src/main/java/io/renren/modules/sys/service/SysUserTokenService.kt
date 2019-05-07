/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service

import com.baomidou.mybatisplus.extension.service.IService
import io.renren.common.utils.R
import io.renren.modules.sys.entity.SysUserTokenEntity

/**
 * 用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysUserTokenService : IService<SysUserTokenEntity> {

    /**
     * 生成token
     * @param userId  用户ID
     */
    fun createToken(userId: Long): R

    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    fun logout(userId: Long)

}