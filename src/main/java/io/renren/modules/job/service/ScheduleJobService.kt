/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.service

import com.baomidou.mybatisplus.extension.service.IService
import io.renren.common.utils.PageUtils
import io.renren.modules.job.entity.ScheduleJobEntity

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
interface ScheduleJobService : IService<ScheduleJobEntity> {

    fun queryPage(params: MutableMap<String, Any>): PageUtils

    /**
     * 保存定时任务
     */
    fun saveJob(scheduleJob: ScheduleJobEntity)

    /**
     * 更新定时任务
     */
    fun update(scheduleJob: ScheduleJobEntity)

    /**
     * 批量删除定时任务
     */
    fun deleteBatch(jobIds: Array<Long>)

    /**
     * 批量更新定时任务状态
     */
    fun updateBatch(jobIds: Array<Long>, status: Int): Int

    /**
     * 立即执行
     */
    fun run(jobIds: Array<Long>)

    /**
     * 暂停运行
     */
    fun pause(jobIds: Array<Long>)

    /**
     * 恢复运行
     */
    fun resume(jobIds: Array<Long>)
}
