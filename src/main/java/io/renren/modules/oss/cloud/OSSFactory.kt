/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.cloud


import io.renren.common.utils.ConfigConstant
import io.renren.common.utils.Constant
import io.renren.common.utils.SpringContextUtils
import io.renren.modules.sys.service.SysConfigService

/**
 * 文件上传Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
object OSSFactory {
    private var sysConfigService: SysConfigService? = null

    init {
        OSSFactory.sysConfigService = SpringContextUtils.getBean("sysConfigService") as SysConfigService
    }

    fun build(): CloudStorageService? {
        //获取云存储配置信息
        val config = sysConfigService!!.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig::class.java)

        when {
            config.type == Constant.CloudService.QINIU.value -> return QiniuCloudStorageService(config)
            config.type == Constant.CloudService.ALIYUN.value -> return AliyunCloudStorageService(config)
            config.type == Constant.CloudService.QCLOUD.value -> return QcloudCloudStorageService(config)
            else -> return null
        }

    }

}
