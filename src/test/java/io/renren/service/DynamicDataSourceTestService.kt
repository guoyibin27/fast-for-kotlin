/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.service

import io.renren.datasource.annotation.DataSource
import io.renren.modules.sys.dao.SysUserDao
import io.renren.modules.sys.entity.SysUserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 测试多数据源
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
//@DataSource("slave1")
class DynamicDataSourceTestService {
    @Autowired
    private val sysUserDao: SysUserDao? = null

    @Transactional
    fun updateUser(id: Long) {
        val user = SysUserEntity()
        user.setUserId(id)
        user.setMobile("13500000000")
        sysUserDao!!.updateById(user)
    }

    @Transactional
    @DataSource("slave1")
    fun updateUserBySlave1(id: Long) {
        val user = SysUserEntity()
        user.setUserId(id)
        user.setMobile("13500000001")
        sysUserDao!!.updateById(user)
    }

    @DataSource("slave2")
    @Transactional
    fun updateUserBySlave2(id: Long) {
        val user = SysUserEntity()
        user.setUserId(id)
        user.setMobile("13500000002")
        sysUserDao!!.updateById(user)

        //测试事物
        val i = 1 / 0
    }
}