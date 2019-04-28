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
 * 密码表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
class PasswordForm {
    /**
     * 原密码
     */
    var password: String? = null
    /**
     * 新密码
     */
    var newPassword: String? = null

}
