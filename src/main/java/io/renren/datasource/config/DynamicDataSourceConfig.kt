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
import io.renren.datasource.properties.DynamicDataSourceProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.HashMap
import javax.sql.DataSource

/**
 * 配置多数据源
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties::class)
 class DynamicDataSourceConfig {

    @Autowired
    lateinit var properties: DynamicDataSourceProperties

    private val dynamicDataSource: Map<Any, Any>
        get() {
            val dataSourcePropertiesMap = properties.datasource
            val targetDataSources = HashMap<Any, Any>(dataSourcePropertiesMap.size)
            dataSourcePropertiesMap.forEach { k, v ->
                val druidDataSource = DynamicDataSourceFactory.buildDruidDataSource(v)
                targetDataSources[k] = druidDataSource
            }

            return targetDataSources
        }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
     fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
     fun dynamicDataSource(dataSourceProperties: DataSourceProperties): DynamicDataSource {
        val dynamicDataSource = DynamicDataSource()
        dynamicDataSource.setTargetDataSources(this.dynamicDataSource)

        //默认数据源
        val defaultDataSource = DynamicDataSourceFactory.buildDruidDataSource(dataSourceProperties)
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource)

        return dynamicDataSource
    }

}