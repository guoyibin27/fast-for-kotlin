/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.controller

import io.renren.common.annotation.SysLog
import io.renren.common.utils.PageUtils
import io.renren.common.utils.R
import io.renren.common.validator.ValidatorUtils
import io.renren.modules.job.entity.ScheduleJobEntity
import io.renren.modules.job.service.ScheduleJobService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/schedule")
class ScheduleJobController {

    @Autowired
    lateinit var scheduleJobService: ScheduleJobService

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = scheduleJobService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    fun info(@PathVariable("jobId") jobId: Long): R {
        val schedule = scheduleJobService.getById(jobId)
        return R.ok().put("schedule", schedule)
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    fun save(@RequestBody scheduleJob: ScheduleJobEntity): R {
        ValidatorUtils.validateEntity(scheduleJob)
        scheduleJobService.saveJob(scheduleJob)
        return R.ok()
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    fun update(@RequestBody scheduleJob: ScheduleJobEntity): R {
        ValidatorUtils.validateEntity(scheduleJob)
        scheduleJobService.update(scheduleJob)
        return R.ok()
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    fun delete(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService.deleteBatch(jobIds)
        return R.ok()
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    fun run(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService.run(jobIds)
        return R.ok()
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    fun pause(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService.pause(jobIds)
        return R.ok()
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    fun resume(@RequestBody jobIds: Array<Long>): R {
        scheduleJobService.resume(jobIds)
        return R.ok()
    }

}
