/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data

import java.io.Serializable

/**
 * 角色与菜单对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_role_menu")
class SysRoleMenuEntity : Serializable {

    @TableId
    var id: Long? = null

    /**
     * 角色ID
     */
    var roleId: Long? = null

    /**
     * 菜单ID
     */
    var menuId: Long? = null
}
