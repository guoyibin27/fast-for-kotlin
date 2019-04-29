/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.modules.sys.dao.SysRoleMenuDao
import io.renren.modules.sys.entity.SysRoleMenuEntity
import io.renren.modules.sys.service.SysRoleMenuService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysRoleMenuService")
 class SysRoleMenuServiceImpl : ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity>(), SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun saveOrUpdate(roleId: Long, menuIdList: List<Long>) {
        //先删除角色与菜单关系
        deleteBatch(arrayOf(roleId))

        if (menuIdList.isEmpty()) {
            return
        }

        //保存角色与菜单关系
        for (menuId in menuIdList) {
            val sysRoleMenuEntity = SysRoleMenuEntity()
            sysRoleMenuEntity.menuId = menuId
            sysRoleMenuEntity.roleId = roleId

            this.save(sysRoleMenuEntity)
        }
    }

    @Override
    override fun queryMenuIdList(roleId: Long): List<Long> {
        return baseMapper.queryMenuIdList(roleId)
    }

    @Override
    override fun deleteBatch(roleIds: Array<Long>): Int {
        return baseMapper.deleteBatch(roleIds)
    }

}
