/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.datasource.config

import java.util.ArrayDeque
import java.util.Deque

/**
 * 多数据源上下文
 *
 * @author Mark sunlightcs@gmail.com
 */
object DynamicContextHolder {

    private val CONTEXT_HOLDER = object : ThreadLocal<Deque<String>>() {
        override fun initialValue(): Deque<String> {
            return ArrayDeque<String>()
        }
    }

    /**
     * 获得当前线程数据源
     *
     * @return 数据源名称ThreadLocal
     */
    fun peek(): String? {
        return CONTEXT_HOLDER.get().peek()
    }

    /**
     * 设置当前线程数据源
     *
     * @param dataSource 数据源名称
     */
    fun push(dataSource: String) {
        CONTEXT_HOLDER.get().push(dataSource)
    }

    /**
     * 清空当前线程数据源
     */
    fun poll() {
        val deque = CONTEXT_HOLDER.get()
        deque.poll()
        if (deque.isEmpty()) {
            CONTEXT_HOLDER.remove()
        }
    }
}