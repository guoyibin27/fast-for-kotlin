/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.form

import lombok.Data

/**
 * 登录表单
 *
 * @author Mark sunlightcs@gmail.com
 */
class SysLoginForm {
    var username: String? = null
    var password: String? = null
    var captcha: String? = null
    var uuid: String? = null


}
