/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.utils.MapUtils
import io.renren.modules.sys.dao.SysUserRoleDao
import io.renren.modules.sys.entity.SysUserRoleEntity
import io.renren.modules.sys.service.SysUserRoleService
import org.springframework.stereotype.Service


/**
 * 用户与角色对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserRoleService")
class SysUserRoleServiceImpl : ServiceImpl<SysUserRoleDao, SysUserRoleEntity>(), SysUserRoleService {

    @Override
    override fun saveOrUpdate(userId: Long, roleIdList: List<Long>?) {
        //先删除用户与角色关系
        this.removeByMap(MapUtils().put("user_id", userId))

        if (roleIdList.isNullOrEmpty()) {
            return
        }

        //保存用户与角色关系
        for (roleId in roleIdList) {
            val sysUserRoleEntity = SysUserRoleEntity()
            sysUserRoleEntity.userId = userId
            sysUserRoleEntity.roleId = roleId

            this.save(sysUserRoleEntity)
        }
    }

    @Override
    override fun queryRoleIdList(userId: Long): List<Long> {
        return baseMapper.queryRoleIdList(userId)
    }

    @Override
    override fun deleteBatch(roleIds: Array<Long>): Int {
        return baseMapper.deleteBatch(roleIds)
    }
}
