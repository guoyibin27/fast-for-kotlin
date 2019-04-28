/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.cloud

import com.aliyun.oss.OSSClient
import io.renren.common.exception.RRException

import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * 阿里云存储
 *
 * @author Mark sunlightcs@gmail.com
 */
class AliyunCloudStorageService(config: CloudStorageConfig) : CloudStorageService(config) {
    private val client: OSSClient by lazy { OSSClient(config.aliyunEndPoint, config.aliyunAccessKeyId, config.aliyunAccessKeySecret) }

    @Override
    override fun upload(data: ByteArray, path: String): String {
        return upload(ByteArrayInputStream(data), path)
    }

    @Override
    override fun upload(inputStream: InputStream, path: String): String {
        try {
            client.putObject(config.aliyunBucketName, path, inputStream)
        } catch (e: Exception) {
            throw RRException("上传文件失败，请检查配置信息", e)
        }

        return config.aliyunDomain + "/" + path
    }

    @Override
    override fun uploadSuffix(data: ByteArray, suffix: String): String {
        return upload(data, getPath(config.aliyunPrefix ?: "", suffix))
    }

    @Override
    override fun uploadSuffix(inputStream: InputStream, suffix: String): String {
        return upload(inputStream, getPath(config.aliyunPrefix ?: "", suffix))
    }
}
