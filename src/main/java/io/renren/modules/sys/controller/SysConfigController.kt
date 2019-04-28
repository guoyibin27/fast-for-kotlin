/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller


import io.renren.common.annotation.SysLog
import io.renren.common.utils.PageUtils
import io.renren.common.utils.R
import io.renren.common.validator.ValidatorUtils
import io.renren.modules.sys.entity.SysConfigEntity
import io.renren.modules.sys.service.SysConfigService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/config")
class SysConfigController : AbstractController() {
    @Autowired
    lateinit var sysConfigService: SysConfigService

    /**
     * 所有配置列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:config:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page = sysConfigService.queryPage(params)

        return R.ok().put("page", page)
    }


    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    fun info(@PathVariable("id") id: Long): R {
        val config = sysConfigService.getById(id)

        return R.ok().put("config", config)
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @PostMapping("/save")
    @RequiresPermissions("sys:config:save")
    fun save(@RequestBody config: SysConfigEntity): R {
        ValidatorUtils.validateEntity(config)

        sysConfigService.saveConfig(config)

        return R.ok()
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @PostMapping("/update")
    @RequiresPermissions("sys:config:update")
    fun update(@RequestBody config: SysConfigEntity): R {
        ValidatorUtils.validateEntity(config)

        sysConfigService.update(config)

        return R.ok()
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @PostMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        sysConfigService.deleteBatch(ids)

        return R.ok()
    }

}
