/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao


import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.renren.modules.sys.entity.SysConfigEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
interface SysConfigDao : BaseMapper<SysConfigEntity> {

    /**
     * 根据key，查询value
     */
    fun queryByKey(paramKey: String): SysConfigEntity?

    /**
     * 根据key，更新value
     */
    fun updateValueByKey(@Param("paramKey") paramKey: String, @Param("paramValue") paramValue: String): Int

}
