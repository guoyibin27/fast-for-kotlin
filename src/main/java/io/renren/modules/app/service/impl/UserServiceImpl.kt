/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.service.impl


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.exception.RRException
import io.renren.common.validator.Assert
import io.renren.modules.app.dao.UserDao
import io.renren.modules.app.entity.UserEntity
import io.renren.modules.app.form.LoginForm
import io.renren.modules.app.service.UserService
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service


@Service("userService")
 class UserServiceImpl : ServiceImpl<UserDao, UserEntity>(), UserService {

    @Override
    override fun queryByMobile(mobile: String): UserEntity {
        return baseMapper.selectOne(QueryWrapper<UserEntity>().eq("mobile", mobile))
    }

    @Override
    override fun login(form: LoginForm): Long {
        val user = queryByMobile(form.mobile!!)
        Assert.isNull(user, "手机号或密码错误")

        //密码错误
        if (!user.password.equals(DigestUtils.sha256Hex(form.password))) {
            throw RRException("手机号或密码错误")
        }

        return user.userId!!
    }
}
