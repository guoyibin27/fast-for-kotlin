/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.exception.RRException
import io.renren.common.utils.Constant
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.sys.dao.SysRoleDao
import io.renren.modules.sys.entity.SysRoleEntity
import io.renren.modules.sys.service.SysRoleMenuService
import io.renren.modules.sys.service.SysRoleService
import io.renren.modules.sys.service.SysUserRoleService
import io.renren.modules.sys.service.SysUserService
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysRoleService")
 class SysRoleServiceImpl : ServiceImpl<SysRoleDao, SysRoleEntity>(), SysRoleService {

    @Autowired
    lateinit var sysRoleMenuService: SysRoleMenuService
    @Autowired
    lateinit var sysUserService: SysUserService
    @Autowired
    lateinit var sysUserRoleService: SysUserRoleService

    @Override
    override fun queryPage(params: MutableMap<String, Any>): PageUtils {
        val roleName = params["roleName"] as String
        val createUserId = params["createUserId"] as? Long

        val page = this.page(
                Query<SysRoleEntity>().getPage(params),
                QueryWrapper<SysRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                        .eq(createUserId != null, "create_user_id", createUserId)
        )

        return PageUtils(page)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun saveRole(role: SysRoleEntity) {
        role.createTime = Date()
        this.save(role)

        //检查权限是否越权
        checkPrems(role)

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.roleId!!, role.menuIdList ?: emptyList())
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun update(role: SysRoleEntity) {
        this.updateById(role)

        //检查权限是否越权
        checkPrems(role)

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.roleId!!, role.menuIdList ?: emptyList())
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun deleteBatch(roleIds: Array<Long>) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds))

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds)

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds)
    }


    @Override
    override fun queryRoleIdList(createUserId: Long): List<Long> {
        return baseMapper.queryRoleIdList(createUserId)
    }

    /**
     * 检查权限是否越权
     */
    private fun checkPrems(role: SysRoleEntity) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (role.createUserId == Constant.SUPER_ADMIN) {
            return
        }

        //查询用户所拥有的菜单列表
        val menuIdList = sysUserService.queryAllMenuId(role.createUserId!!)

        //判断是否越权
        if (!menuIdList.containsAll(role.menuIdList ?: emptyList())) {
            throw RRException("新增角色的权限，已超出你的权限范围")
        }
    }
}
