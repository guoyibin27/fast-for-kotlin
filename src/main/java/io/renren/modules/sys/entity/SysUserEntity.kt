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
import io.renren.common.validator.group.AddGroup
import io.renren.common.validator.group.UpdateGroup
import lombok.Data

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import java.io.Serializable
import java.util.Date

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@TableName("sys_user")
class SysUserEntity : Serializable {

    /**
     * 用户ID
     */
    @TableId
    var userId: Long? = null

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = [AddGroup::class, UpdateGroup::class])
    var username: String? = null

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = [AddGroup::class])
    var password: String? = null

    /**
     * 盐
     */
    var salt: String? = null

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = [AddGroup::class, UpdateGroup::class])
    @Email(message = "邮箱格式不正确", groups = [AddGroup::class, UpdateGroup::class])
    var email: String? = null

    /**
     * 手机号
     */
    var mobile: String? = null

    /**
     * 状态  0：禁用   1：正常
     */
    var status: Int? = null

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    var roleIdList: List<Long>? = null

    /**
     * 创建者ID
     */
    var createUserId: Long? = null

    /**
     * 创建时间
     */
    var createTime: Date? = null
}
