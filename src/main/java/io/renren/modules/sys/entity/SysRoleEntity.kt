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

import javax.validation.constraints.NotBlank
import java.io.Serializable
import java.util.Date

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_role")
class SysRoleEntity : Serializable {

    /**
     * 角色ID
     */
    @TableId
    var roleId: Long? = null

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    var roleName: String? = null

    /**
     * 备注
     */
    var remark: String? = null

    /**
     * 创建者ID
     */
    var createUserId: Long? = null

    @TableField(exist = false)
    var menuIdList: List<Long>? = null

    /**
     * 创建时间
     */
    var createTime: Date? = null
}
