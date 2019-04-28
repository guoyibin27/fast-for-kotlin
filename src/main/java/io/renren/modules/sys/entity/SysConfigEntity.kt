/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data

import javax.validation.constraints.NotBlank

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_config")
class SysConfigEntity {
    @TableId
    var  id: Long? = null
    @NotBlank(message = "参数名不能为空")
    var  paramKey: String? = null
    @NotBlank(message = "参数值不能为空")
    var  paramValue: String? = null
    var  remark: String? = null

}
