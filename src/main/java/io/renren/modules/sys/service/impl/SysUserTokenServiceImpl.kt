/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.utils.R
import io.renren.modules.sys.dao.SysUserTokenDao
import io.renren.modules.sys.entity.SysUserTokenEntity
import io.renren.modules.sys.oauth2.TokenGenerator
import io.renren.modules.sys.service.SysUserTokenService
import org.springframework.stereotype.Service

import java.util.Date


@Service("sysUserTokenService")
 class SysUserTokenServiceImpl : ServiceImpl<SysUserTokenDao, SysUserTokenEntity>(), SysUserTokenService {


    @Override
    override fun createToken(userId: Long): R {
        //生成一个token
        val token = TokenGenerator.generateValue()

        //当前时间
        val now = Date()
        //过期时间
        val expireTime = Date(now.time + EXPIRE * 1000)

        //判断是否生成过token
        var tokenEntity: SysUserTokenEntity? = this.getById(userId)
        if (tokenEntity == null) {
            tokenEntity = SysUserTokenEntity()
            tokenEntity.userId = userId
            tokenEntity.token = token
            tokenEntity.updateTime = now
            tokenEntity.expireTime = expireTime

            //保存token
            this.save(tokenEntity)
        } else {
            tokenEntity.token = token
            tokenEntity.updateTime = now
            tokenEntity.expireTime = expireTime

            //更新token
            this.updateById(tokenEntity)
        }

        val r = R.ok().put("token", token!!).put("expire", EXPIRE)

        return r
    }

    @Override
    public override fun logout(userId: Long) {
        //生成一个token
        val token = TokenGenerator.generateValue()

        //修改token
        val tokenEntity = SysUserTokenEntity()
        tokenEntity.userId = userId
        tokenEntity.token = token
        this.updateById(tokenEntity)
    }

    companion object {
        //12小时后过期
        private const val EXPIRE = 3600 * 12
    }
}
