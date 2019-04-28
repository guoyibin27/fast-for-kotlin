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

import javax.validation.constraints.NotBlank
import java.io.Serializable
import java.util.Date

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("schedule_job")
class ScheduleJobEntity : Serializable {

    /**
     * 任务id
     */
    @TableId
    var jobId: Long? = null

    /**
     * spring bean名称
     */
    @NotBlank(message = "bean名称不能为空")
    var beanName: String? = null

    /**
     * 参数
     */
    var params: String? = null

    /**
     * cron表达式
     */
    @NotBlank(message = "cron表达式不能为空")
    var cronExpression: String? = null

    /**
     * 任务状态
     */
    var status: Int? = null

    /**
     * 备注
     */
    var remark: String? = null

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: Date? = null

    companion object {
        /**
         * 任务调度参数key
         */
        val JOB_PARAM_KEY = "JOB_PARAM_KEY"
    }

}
