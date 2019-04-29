/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.google.code.kaptcha.Producer
import io.renren.common.exception.RRException
import io.renren.common.utils.DateUtils
import io.renren.modules.sys.dao.SysCaptchaDao
import io.renren.modules.sys.entity.SysCaptchaEntity
import io.renren.modules.sys.service.SysCaptchaService
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.awt.image.BufferedImage
import java.util.Date

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysCaptchaService")
 class SysCaptchaServiceImpl : ServiceImpl<SysCaptchaDao, SysCaptchaEntity>(), SysCaptchaService {
    @Autowired
    private val producer: Producer? = null

    @Override
    override fun getCaptcha(uuid: String): BufferedImage {
        if (StringUtils.isBlank(uuid)) {
            throw RRException("uuid不能为空")
        }
        //生成文字验证码
        val code = producer!!.createText()

        val captchaEntity = SysCaptchaEntity()
        captchaEntity.uuid = uuid
        captchaEntity.code = code
        //5分钟后过期
        captchaEntity.expireTime = DateUtils.addDateMinutes(Date(), 5)
        this.save(captchaEntity)

        return producer.createImage(code)
    }

    @Override
    override fun validate(uuid: String, code: String): Boolean {
        val captchaEntity = this.getOne(QueryWrapper<SysCaptchaEntity>().eq("uuid", uuid)) ?: return false

        //删除验证码
        this.removeById(uuid)

        return captchaEntity.code.equals(code, true) && captchaEntity.expireTime!!.time >= System.currentTimeMillis()
    }
}
