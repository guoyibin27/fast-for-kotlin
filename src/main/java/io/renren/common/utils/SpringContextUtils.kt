/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * Spring Context 工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
class SpringContextUtils : ApplicationContextAware {

    companion object {
        @JvmStatic
        private var applicationCtx: ApplicationContext? = null

        fun getBean(name: String): Any? {
            return applicationCtx?.getBean(name)
        }

        fun <T> getBean(name: String, requiredType: Class<T>): T? {
            return applicationCtx?.getBean(name, requiredType)
        }

        fun containsBean(name: String): Boolean {
            return applicationCtx?.containsBean(name) ?: false
        }

        fun isSingleton(name: String): Boolean {
            return applicationCtx?.isSingleton(name) ?: false
        }

        fun getType(name: String): Class<out Any>? {
            return applicationCtx?.getType(name)
        }
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextUtils.applicationCtx = applicationContext
    }
}