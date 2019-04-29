/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.google.gson.Gson
import io.renren.common.exception.RRException
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.sys.dao.SysConfigDao
import io.renren.modules.sys.entity.SysConfigEntity
import io.renren.modules.sys.redis.SysConfigRedis
import io.renren.modules.sys.service.SysConfigService
import oracle.sql.Mutable
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Arrays

@Service("sysConfigService")
 class SysConfigServiceImpl : ServiceImpl<SysConfigDao, SysConfigEntity>(), SysConfigService {
    @Autowired
    private val sysConfigRedis: SysConfigRedis? = null

    @Override
    override fun queryPage(params: MutableMap<String, Any>): PageUtils {
        val paramKey = params["paramKey"] as String

        val page = this.page(
                Query<SysConfigEntity>().getPage(params),
                QueryWrapper<SysConfigEntity>()
                        .like(StringUtils.isNotBlank(paramKey), "param_key", paramKey)
                        .eq("status", 1)
        )

        return PageUtils(page)
    }

    @Override
    override fun saveConfig(config: SysConfigEntity) {
        this.save(config)
        sysConfigRedis!!.saveOrUpdate(config)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun update(config: SysConfigEntity) {
        this.updateById(config)
        sysConfigRedis!!.saveOrUpdate(config)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun updateValueByKey(key: String, value: String) {
        baseMapper.updateValueByKey(key, value)
        sysConfigRedis!!.delete(key)
    }

    @Override
    @Transactional(rollbackFor = [Exception::class])
    override fun deleteBatch(ids: Array<Long>) {
        for (id in ids) {
            val config = this.getById(id)
            sysConfigRedis!!.delete(config.paramKey!!)
        }

        this.removeByIds(Arrays.asList(ids))
    }

    @Override
    override fun getValue(key: String): String? {
        var config: SysConfigEntity? = sysConfigRedis!!.get(key)
        if (config == null) {
            config = baseMapper.queryByKey(key)
            sysConfigRedis.saveOrUpdate(config)
        }

        return config?.paramValue
    }

    @Override
    override fun <T> getConfigObject(key: String, clazz: Class<T>): T {
        val value = getValue(key)
        if (StringUtils.isNotBlank(value)) {
            return Gson().fromJson(value, clazz)
        }

        try {
            return clazz.newInstance()
        } catch (e: Exception) {
            throw RRException("获取参数失败")
        }

    }
}
