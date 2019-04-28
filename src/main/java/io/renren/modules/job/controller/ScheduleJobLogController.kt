/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.controller

import io.renren.common.utils.PageUtils
import io.renren.common.utils.R
import io.renren.modules.job.entity.ScheduleJobLogEntity
import io.renren.modules.job.service.ScheduleJobLogService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/scheduleLog")
class ScheduleJobLogController {

    @Autowired
    lateinit var scheduleJobLogService: ScheduleJobLogService

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = scheduleJobLogService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    fun info(@PathVariable("logId") logId: Long): R {
        val log = scheduleJobLogService.getById(logId)
        return R.ok().put("log", log)
    }
}
