/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data

import java.util.Date

/**
 * 系统验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_captcha")
class SysCaptchaEntity {
    @TableId(type = IdType.INPUT)
    var  uuid: String? = null
    /**
     * 验证码
     */
    var  code: String? = null
    /**
     * 过期时间
     */
    var  expireTime: Date? = null

}
