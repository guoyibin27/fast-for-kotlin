/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean

import javax.sql.DataSource
import java.util.Properties

/**
 * 定时任务配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
open class ScheduleConfig {

    @Bean
    open fun schedulerFactoryBean(dataSource: DataSource): SchedulerFactoryBean {
        val factory = SchedulerFactoryBean()
        factory.setDataSource(dataSource)

        //quartz参数
        val prop = Properties()
        prop["org.quartz.scheduler.instanceName"] = "RenrenScheduler"
        prop["org.quartz.scheduler.instanceId"] = "AUTO"
        //线程池配置
        prop["org.quartz.threadPool.class"] = "org.quartz.simpl.SimpleThreadPool"
        prop["org.quartz.threadPool.threadCount"] = "20"
        prop["org.quartz.threadPool.threadPriority"] = "5"
        //JobStore配置
        prop["org.quartz.jobStore.class"] = "org.quartz.impl.jdbcjobstore.JobStoreTX"
        //集群配置
        prop["org.quartz.jobStore.isClustered"] = "true"
        prop["org.quartz.jobStore.clusterCheckinInterval"] = "15000"
        prop["org.quartz.jobStore.maxMisfiresToHandleAtATime"] = "1"

        prop["org.quartz.jobStore.misfireThreshold"] = "12000"
        prop["org.quartz.jobStore.tablePrefix"] = "QRTZ_"
        prop["org.quartz.jobStore.selectWithLockSQL"] = "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?"

        //PostgreSQL数据库，需要打开此注释
        //prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");

        factory.setQuartzProperties(prop)

        factory.setSchedulerName("RenrenScheduler")
        //延时启动
        factory.setStartupDelay(30)
        factory.setApplicationContextSchedulerContextKey("applicationContextKey")
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true)
        //设置自动启动，默认为true
        factory.isAutoStartup = true

        return factory
    }
}
