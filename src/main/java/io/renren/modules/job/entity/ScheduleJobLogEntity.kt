/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data

import java.io.Serializable
import java.util.Date

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("schedule_job_log")
class ScheduleJobLogEntity : Serializable {

    /**
     * 日志id
     */
    @TableId
    var logId: Long? = null

    /**
     * 任务id
     */
    var jobId: Long? = null

    /**
     * spring bean名称
     */
    var beanName: String? = null

    /**
     * 参数
     */
    var params: String? = null

    /**
     * 任务状态    0：成功    1：失败
     */
    var status: Int? = null

    /**
     * 失败信息
     */
    var error: String? = null

    /**
     * 耗时(单位：毫秒)
     */
    var times: Int? = null

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: Date? = null
}
