/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren

import io.renren.service.DynamicDataSourceTestService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * 多数据源测试
 *
 * @author Mark sunlightcs@gmail.com
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class DynamicDataSourceTest {
    @Autowired
    private val dynamicDataSourceTestService: DynamicDataSourceTestService? = null

    @Test
    fun test() {
        val id = 1L

        dynamicDataSourceTestService!!.updateUser(id)
        dynamicDataSourceTestService.updateUserBySlave1(id)
        dynamicDataSourceTestService.updateUserBySlave2(id)
    }

}
