/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.utils.Constant
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.job.dao.ScheduleJobDao
import io.renren.modules.job.entity.ScheduleJobEntity
import io.renren.modules.job.service.ScheduleJobService
import io.renren.modules.job.utils.ScheduleUtils
import org.apache.commons.lang.StringUtils
import org.quartz.CronTrigger
import org.quartz.Scheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.annotation.PostConstruct
import java.util.*

@Service("scheduleJobService")
class ScheduleJobServiceImpl : ServiceImpl<ScheduleJobDao, ScheduleJobEntity>(), ScheduleJobService {
    @Autowired
    private val scheduler: Scheduler? = null

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    fun init() {
        val scheduleJobList = this.list()
        for (scheduleJob in scheduleJobList) {
            val cronTrigger = ScheduleUtils.getCronTrigger(scheduler!!, scheduleJob.getJobId())
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob)
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob)
            }
        }
    }

    @Override
    override fun queryPage(params: Map<String, Any>): PageUtils {
        val beanName = params["beanName"] as String

        val page = this.page(
                Query<ScheduleJobEntity>().getPage(params),
                QueryWrapper<ScheduleJobEntity>().like(StringUtils.isNotBlank(beanName), "bean_name", beanName)
        )

        return PageUtils(page)
    }


    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun saveJob(scheduleJob: ScheduleJobEntity) {
        scheduleJob.setCreateTime(Date())
        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.value)
        this.save(scheduleJob)

        ScheduleUtils.createScheduleJob(scheduler!!, scheduleJob)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun update(scheduleJob: ScheduleJobEntity) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob)

        this.updateById(scheduleJob)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun deleteBatch(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler!!, jobId)
        }

        //删除数据
        this.removeByIds(Arrays.asList(jobIds))
    }

    @Override
    override fun updateBatch(jobIds: Array<Long>, status: Int): Int {
        val map = HashMap(2)
        map.put("list", jobIds)
        map.put("status", status)
        return baseMapper.updateBatch(map)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun run(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.run(scheduler!!, this.getById(jobId))
        }
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun pause(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.pauseJob(scheduler!!, jobId)
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.value)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun resume(jobIds: Array<Long>) {
        for (jobId in jobIds) {
            ScheduleUtils.resumeJob(scheduler!!, jobId)
        }

        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.value)
    }

}
