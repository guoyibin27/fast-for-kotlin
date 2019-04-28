/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.task

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component("testTask")
class TestTask : ITask {
    private val logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Override
    override fun run(params: String) {
        logger.debug("TestTask定时任务正在执行，参数为：{}", params)
    }
}
