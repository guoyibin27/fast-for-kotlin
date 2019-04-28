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
import io.renren.modules.job.entity.ScheduleJobLogEntity

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
interface ScheduleJobLogService : IService<ScheduleJobLogEntity> {

    fun queryPage(params: MutableMap<String, Any>): PageUtils

}
