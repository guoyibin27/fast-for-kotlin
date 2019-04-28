/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data

import java.io.Serializable
import java.util.Date


/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("tb_user")
class UserEntity : Serializable {

    /**
     * 用户ID
     */
    @TableId
    var userId: Long? = null
    /**
     * 用户名
     */
    var username: String? = null
    /**
     * 手机号
     */
    var mobile: String? = null
    /**
     * 密码
     */
    var password: String? = null
    /**
     * 创建时间
     */
    var createTime: Date? = null
}
