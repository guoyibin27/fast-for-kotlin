/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl

import io.renren.common.utils.Constant
import io.renren.modules.sys.dao.SysMenuDao
import io.renren.modules.sys.dao.SysUserDao
import io.renren.modules.sys.dao.SysUserTokenDao
import io.renren.modules.sys.entity.SysMenuEntity
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.entity.SysUserTokenEntity
import io.renren.modules.sys.service.ShiroService
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.*

@Service
class ShiroServiceImpl : ShiroService {
    @Autowired
    lateinit var sysMenuDao: SysMenuDao
    @Autowired
    lateinit var sysUserDao: SysUserDao
    @Autowired
    lateinit var sysUserTokenDao: SysUserTokenDao

    @Override
    override fun getUserPermissions(userId: Long): Set<String> {
        //系统管理员，拥有最高权限
        val permsList = if (userId == Constant.SUPER_ADMIN.toLong()) {
            val menuList = sysMenuDao.selectList(null)
            menuList.map { it.perms }.toList()
        } else {
            sysUserDao.queryAllPerms(userId)
        }
        //用户权限列表
        val permsSet = hashSetOf<String>()
        for (perms in permsList) {
            if (perms.isNullOrEmpty()) {
                continue
            }
            permsSet.addAll(perms.trim().split(","))
        }
        return permsSet
    }

    @Override
    override fun queryByToken(token: String): SysUserTokenEntity? {
        return sysUserTokenDao.queryByToken(token)
    }

    @Override
    override fun queryUser(userId: Long): SysUserEntity? {
        return sysUserDao.selectById(userId)
    }
}
