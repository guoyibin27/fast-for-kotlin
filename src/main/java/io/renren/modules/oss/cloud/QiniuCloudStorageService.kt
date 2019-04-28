/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.cloud

import com.qiniu.common.Zone
import com.qiniu.http.Response
import com.qiniu.storage.Configuration
import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth
import io.renren.common.exception.RRException
import org.apache.commons.io.IOUtils

import java.io.IOException
import java.io.InputStream

/**
 * 七牛云存储
 *
 * @author Mark sunlightcs@gmail.com
 */
class QiniuCloudStorageService(config: CloudStorageConfig) : CloudStorageService(config) {
    private val uploadManager: UploadManager by lazy { UploadManager(Configuration(Zone.autoZone())) }
    private val token: String by lazy { Auth.create(config.qiniuAccessKey, config.qiniuSecretKey).uploadToken(config.qiniuBucketName) }

    @Override
    override fun upload(data: ByteArray, path: String): String {
        try {
            val res = uploadManager.put(data, path, token)
            if (!res.isOK) {
                throw RuntimeException("上传七牛出错：$res")
            }
        } catch (e: Exception) {
            throw RRException("上传文件失败，请核对七牛配置信息", e)
        }

        return config.qiniuDomain + "/" + path
    }

    @Override
    override fun upload(inputStream: InputStream, path: String): String {
        try {
            val data = IOUtils.toByteArray(inputStream)
            return this.upload(data, path)
        } catch (e: IOException) {
            throw RRException("上传文件失败", e)
        }

    }

    @Override
    override fun uploadSuffix(data: ByteArray, suffix: String): String {
        return upload(data, getPath(config.qiniuPrefix ?: "", suffix))
    }

    @Override
    override fun uploadSuffix(inputStream: InputStream, suffix: String): String {
        return upload(inputStream, getPath(config.qiniuPrefix ?: "", suffix))
    }
}
