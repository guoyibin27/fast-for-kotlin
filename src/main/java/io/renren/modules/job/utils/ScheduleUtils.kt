/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.utils

import io.renren.common.exception.RRException
import io.renren.common.utils.Constant
import io.renren.modules.job.entity.ScheduleJobEntity
import org.quartz.*

/**
 * 定时任务工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
object ScheduleUtils {
    private val JOB_NAME = "TASK_"

    /**
     * 获取触发器key
     */
    fun getTriggerKey(jobId: Long): TriggerKey {
        return TriggerKey.triggerKey(JOB_NAME + jobId)
    }

    /**
     * 获取jobKey
     */
    fun getJobKey(jobId: Long): JobKey {
        return JobKey.jobKey(JOB_NAME + jobId)
    }

    /**
     * 获取表达式触发器
     */
    fun getCronTrigger(scheduler: Scheduler, jobId: Long): CronTrigger? {
        try {
            return scheduler.getTrigger(getTriggerKey(jobId)) as? CronTrigger
        } catch (e: SchedulerException) {
            throw RRException("获取定时任务CronTrigger出现异常", e)
        }

    }

    /**
     * 创建定时任务
     */
    fun createScheduleJob(scheduler: Scheduler, scheduleJob: ScheduleJobEntity) {
        try {
            //构建job信息
            val jobDetail = JobBuilder.newJob(ScheduleJob::class.java).withIdentity(getJobKey(scheduleJob.jobId!!)).build()

            //表达式调度构建器
            val scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.cronExpression)
                    .withMisfireHandlingInstructionDoNothing()

            //按新的cronExpression表达式构建一个新的trigger
            val trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.jobId!!)).withSchedule<CronTrigger>(scheduleBuilder).build()

            //放入参数，运行时的方法可以获取
            jobDetail.jobDataMap[ScheduleJobEntity.JOB_PARAM_KEY] = scheduleJob

            scheduler.scheduleJob(jobDetail, trigger)

            //暂停任务
            if (scheduleJob.status == Constant.ScheduleStatus.PAUSE.value) {
                pauseJob(scheduler, scheduleJob.jobId!!)
            }
        } catch (e: SchedulerException) {
            throw RRException("创建定时任务失败", e)
        }

    }

    /**
     * 更新定时任务
     */
    fun updateScheduleJob(scheduler: Scheduler, scheduleJob: ScheduleJobEntity) {
        try {
            val triggerKey = getTriggerKey(scheduleJob.jobId!!)

            //表达式调度构建器
            val scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.cronExpression)
                    .withMisfireHandlingInstructionDoNothing()

            var trigger = getCronTrigger(scheduler, scheduleJob.jobId!!)

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger?.triggerBuilder?.withIdentity(triggerKey)?.withSchedule<CronTrigger>(scheduleBuilder)?.build()

            //参数
            trigger?.jobDataMap?.set(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob)

            scheduler.rescheduleJob(triggerKey, trigger)

            //暂停任务
            if (scheduleJob.status == Constant.ScheduleStatus.PAUSE.value) {
                pauseJob(scheduler, scheduleJob.jobId!!)
            }

        } catch (e: SchedulerException) {
            throw RRException("更新定时任务失败", e)
        }

    }

    /**
     * 立即执行任务
     */
    fun run(scheduler: Scheduler, scheduleJob: ScheduleJobEntity) {
        try {
            //参数
            val dataMap = JobDataMap()
            dataMap[ScheduleJobEntity.JOB_PARAM_KEY] = scheduleJob

            scheduler.triggerJob(getJobKey(scheduleJob.jobId!!), dataMap)
        } catch (e: SchedulerException) {
            throw RRException("立即执行定时任务失败", e)
        }

    }

    /**
     * 暂停任务
     */
    fun pauseJob(scheduler: Scheduler, jobId: Long) {
        try {
            scheduler.pauseJob(getJobKey(jobId))
        } catch (e: SchedulerException) {
            throw RRException("暂停定时任务失败", e)
        }

    }

    /**
     * 恢复任务
     */
    fun resumeJob(scheduler: Scheduler, jobId: Long) {
        try {
            scheduler.resumeJob(getJobKey(jobId))
        } catch (e: SchedulerException) {
            throw RRException("暂停定时任务失败", e)
        }

    }

    /**
     * 删除定时任务
     */
    fun deleteScheduleJob(scheduler: Scheduler, jobId: Long) {
        try {
            scheduler.deleteJob(getJobKey(jobId))
        } catch (e: SchedulerException) {
            throw RRException("删除定时任务失败", e)
        }

    }
}
