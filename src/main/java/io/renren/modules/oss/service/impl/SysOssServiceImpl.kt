/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.oss.dao.SysOssDao
import io.renren.modules.oss.entity.SysOssEntity
import io.renren.modules.oss.service.SysOssService
import org.springframework.stereotype.Service


@Service("sysOssService")
class SysOssServiceImpl : ServiceImpl<SysOssDao, SysOssEntity>(), SysOssService {

    @Override
    override fun queryPage(params: MutableMap<String, Any>): PageUtils {
        val page = this.page(
                Query<SysOssEntity>().getPage(params)
        )

        return PageUtils(page)
    }

}
