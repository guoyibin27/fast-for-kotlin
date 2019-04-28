/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data

import java.io.Serializable

/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_menu")
class SysMenuEntity : Serializable {

    /**
     * 菜单ID
     */
    @TableId
    var menuId: Long? = null

    /**
     * 父菜单ID，一级菜单为0
     */
    var parentId: Long? = null

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    var parentName: String? = null

    /**
     * 菜单名称
     */
    var name: String? = null

    /**
     * 菜单URL
     */
    var url: String? = null

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    var perms: String? = null

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    var type: Int? = null

    /**
     * 菜单图标
     */
    var icon: String? = null

    /**
     * 排序
     */
    var orderNum: Int? = null

    /**
     * ztree属性
     */
    @TableField(exist = false)
    var open: Boolean? = null

    @TableField(exist = false)
    var list: List<*>? = null

}
