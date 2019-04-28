/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.datasource.config

import com.alibaba.druid.pool.DruidDataSource
import io.renren.datasource.properties.DataSourceProperties

import java.sql.SQLException

/**
 * DruidDataSource
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
object DynamicDataSourceFactory {

    fun buildDruidDataSource(properties: DataSourceProperties): DruidDataSource {
        val druidDataSource = DruidDataSource()
        druidDataSource.driverClassName = properties.driverClassName
        druidDataSource.url = properties.url
        druidDataSource.username = properties.username
        druidDataSource.password = properties.password

        druidDataSource.initialSize = properties.initialSize
        druidDataSource.maxActive = properties.maxActive
        druidDataSource.minIdle = properties.minIdle
        druidDataSource.maxWait = properties.maxWait
        druidDataSource.timeBetweenEvictionRunsMillis = properties.timeBetweenEvictionRunsMillis
        druidDataSource.minEvictableIdleTimeMillis = properties.minEvictableIdleTimeMillis
        druidDataSource.maxEvictableIdleTimeMillis = properties.maxEvictableIdleTimeMillis
        druidDataSource.validationQuery = properties.validationQuery
        druidDataSource.validationQueryTimeout = properties.validationQueryTimeout
        druidDataSource.isTestOnBorrow = properties.isTestOnBorrow
        druidDataSource.isTestOnReturn = properties.isTestOnReturn
        druidDataSource.isPoolPreparedStatements = properties.isPoolPreparedStatements
        druidDataSource.maxOpenPreparedStatements = properties.maxOpenPreparedStatements
        druidDataSource.isSharePreparedStatements = properties.isSharePreparedStatements

        try {
            druidDataSource.setFilters(properties.filters)
            druidDataSource.init()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return druidDataSource
    }
}