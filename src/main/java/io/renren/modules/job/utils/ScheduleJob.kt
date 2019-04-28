/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.utils

import io.renren.common.utils.SpringContextUtils
import io.renren.modules.job.entity.ScheduleJobEntity
import io.renren.modules.job.entity.ScheduleJobLogEntity
import io.renren.modules.job.service.ScheduleJobLogService
import org.apache.commons.lang.StringUtils
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean

import java.lang.reflect.Method
import java.util.Date


/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
class ScheduleJob : QuartzJobBean() {
    private val logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Override
    @Throws(JobExecutionException::class)
    override fun executeInternal(context: JobExecutionContext) {
        val scheduleJob = context.mergedJobDataMap
                .get(ScheduleJobEntity.JOB_PARAM_KEY) as ScheduleJobEntity

        //获取spring bean
        val scheduleJobLogService = SpringContextUtils.getBean("scheduleJobLogService") as ScheduleJobLogService

        //数据库保存执行记录
        val log = ScheduleJobLogEntity()
        log.jobId = scheduleJob.jobId
        log.beanName = scheduleJob.beanName
        log.params = scheduleJob.params
        log.createTime = scheduleJob.createTime

        //任务开始时间
        val startTime = System.currentTimeMillis()

        try {
            //执行任务
            logger.debug("任务准备执行，任务ID：" + scheduleJob.jobId)

            val target = SpringContextUtils.getBean(scheduleJob.beanName!!)
            val method = target?.javaClass?.getDeclaredMethod("run", String::class.java)
            method?.invoke(target, scheduleJob.params)

            //任务执行总时长
            val times = System.currentTimeMillis() - startTime
            log.times = times.toInt()
            //任务状态    0：成功    1：失败
            log.status = 0

            logger.debug("任务执行完毕，任务ID：" + scheduleJob.jobId + "  总共耗时：" + times + "毫秒")
        } catch (e: Exception) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.jobId, e)

            //任务执行总时长
            val times = System.currentTimeMillis() - startTime
            log.times = times.toInt()

            //任务状态    0：成功    1：失败
            log.status = 1
            log.error = (StringUtils.substring(e.toString(), 0, 2000))
        } finally {
            scheduleJobLogService.save(log)
        }
    }
}
