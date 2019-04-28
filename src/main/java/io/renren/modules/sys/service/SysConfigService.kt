/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service

import com.baomidou.mybatisplus.extension.service.IService
import io.renren.common.utils.PageUtils
import io.renren.modules.sys.entity.SysConfigEntity
import oracle.sql.Mutable

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysConfigService : IService<SysConfigEntity> {

    fun queryPage(params: MutableMap<String, Any>): PageUtils

    /**
     * 保存配置信息
     */
    fun saveConfig(config: SysConfigEntity)

    /**
     * 更新配置信息
     */
    fun update(config: SysConfigEntity)

    /**
     * 根据key，更新value
     */
    fun updateValueByKey(key: String, value: String)

    /**
     * 删除配置信息
     */
    fun deleteBatch(ids: Array<Long>)

    /**
     * 根据key，获取配置的value值
     *
     * @param key           key
     */
    fun getValue(key: String): String?

    /**
     * 根据key，获取value的Object对象
     * @param key    key
     * @param clazz  Object对象
     */
    fun <T> getConfigObject(key: String, clazz: Class<T>): T

}
