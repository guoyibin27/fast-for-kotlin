/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
object Constant {
    /** 超级管理员ID  */
    const val SUPER_ADMIN = 1L
    /**
     * 当前页码
     */
    const val PAGE = "page"
    /**
     * 每页显示记录数
     */
    const val LIMIT = "limit"
    /**
     * 排序字段
     */
    const val ORDER_FIELD = "sidx"
    /**
     * 排序方式
     */
    const val ORDER = "order"
    /**
     * 升序
     */
    const val ASC = "asc"

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    enum class MenuType private constructor(val value: Int) {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2)
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    enum class ScheduleStatus private constructor(val value: Int) {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1)
    }

    /**
     * 云服务商
     */
    enum class CloudService private constructor(val value: Int) {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3)
    }

}
