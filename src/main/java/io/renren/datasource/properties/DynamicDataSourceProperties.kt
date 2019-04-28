/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.datasource.properties

import org.springframework.boot.context.properties.ConfigurationProperties

import java.util.LinkedHashMap

/**
 * 多数据源属性
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "dynamic")
class DynamicDataSourceProperties {
    var datasource: Map<String, DataSourceProperties> = LinkedHashMap()
}
