/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.datasource.properties

/**
 * 多数据源属性
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
class DataSourceProperties {
    var driverClassName: String? = null
    var url: String? = null
    var username: String? = null
    var password: String? = null

    /**
     * Druid默认参数
     */
    var initialSize = 2
    var maxActive = 10
    var minIdle = -1
    var maxWait = 60 * 1000L
    var timeBetweenEvictionRunsMillis = 60 * 1000L
    var minEvictableIdleTimeMillis = 1000L * 60L * 30L
    var maxEvictableIdleTimeMillis = 1000L * 60L * 60L * 7
    var validationQuery = "select 1"
    var validationQueryTimeout = -1
    var isTestOnBorrow = false
    var isTestOnReturn = false
    var isTestWhileIdle = true
    var isPoolPreparedStatements = false
    var maxOpenPreparedStatements = -1
    var isSharePreparedStatements = false
    var filters = "stat,wall"
}