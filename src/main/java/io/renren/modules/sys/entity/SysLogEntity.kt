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
import java.util.Date


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_log")
class SysLogEntity : Serializable {
    @TableId
    var id: Long? = null
    //用户名
    var username: String? = null
    //用户操作
    var operation: String? = null
    //请求方法
    var method: String? = null
    //请求参数
    var params: String? = null
    //执行时长(毫秒)
    var time: Long? = null
    //IP地址
    var ip: String? = null
    //创建时间
    var createDate: Date? = null
}
