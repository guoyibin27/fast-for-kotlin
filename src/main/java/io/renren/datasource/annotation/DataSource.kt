/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.datasource.annotation

import java.lang.annotation.Documented
import java.lang.annotation.Inherited

/**
 * 多数据源注解
 *
 * @author Mark sunlightcs@gmail.com
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class DataSource(val value: String = "")
