/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.cloud

import io.renren.common.utils.DateUtils
import org.apache.commons.lang.StringUtils

import java.io.InputStream
import java.util.Date
import java.util.UUID

/**
 * 云存储(支持七牛、阿里云、腾讯云、又拍云)
 *
 * @author Mark sunlightcs@gmail.com
 */
abstract class CloudStorageService(protected val config: CloudStorageConfig) {

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    fun getPath(prefix: String, suffix: String): String {
        //生成uuid
        val uuid = UUID.randomUUID().toString().replace("-", "")
        //文件路径
        var path = DateUtils.format(Date(), "yyyyMMdd").toString() + "/" + uuid

        if (StringUtils.isNotBlank(prefix)) {
            path = "$prefix/$path"
        }

        return path + suffix
    }

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    abstract fun upload(data: ByteArray, path: String): String

    /**
     * 文件上传
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return         返回http地址
     */
    abstract fun uploadSuffix(data: ByteArray, suffix: String): String

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    abstract fun upload(inputStream: InputStream, path: String): String

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return             返回http地址
     */
    abstract fun uploadSuffix(inputStream: InputStream, suffix: String): String

}
