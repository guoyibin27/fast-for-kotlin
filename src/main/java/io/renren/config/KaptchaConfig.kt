/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.config

import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.Properties


/**
 * 生成验证码配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
 class KaptchaConfig {

    @Bean
     fun producer(): DefaultKaptcha {
        val properties = Properties()
        properties["kaptcha.border"] = "no"
        properties["kaptcha.textproducer.font.color"] = "black"
        properties["kaptcha.textproducer.char.space"] = "5"
        properties["kaptcha.textproducer.font.names"] = "Arial,Courier,cmr10,宋体,楷体,微软雅黑"
        val config = Config(properties)
        val defaultKaptcha = DefaultKaptcha()
        defaultKaptcha.config = config
        return defaultKaptcha
    }
}
