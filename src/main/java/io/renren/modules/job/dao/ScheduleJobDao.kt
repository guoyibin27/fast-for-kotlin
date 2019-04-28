/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.renren.modules.job.entity.ScheduleJobEntity
import org.apache.ibatis.annotations.Mapper

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
interface ScheduleJobDao : BaseMapper<ScheduleJobEntity> {

    /**
     * 批量更新状态
     */
    fun updateBatch(map: Map<String, Any>): Int
}
