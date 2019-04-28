/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.exception

/**
 * 自定义异常
 *
 * @author Mark sunlightcs@gmail.com
 */
class RRException : RuntimeException {

    var msg: String? = null
    var code = 500

    constructor(msg: String) : super(msg) {
        this.msg = msg
    }

    constructor(msg: String, e: Throwable) : super(msg, e) {
        this.msg = msg
    }

    constructor(msg: String, code: Int) : super(msg) {
        this.msg = msg
        this.code = code
    }

    constructor(msg: String, code: Int, e: Throwable) : super(msg, e) {
        this.msg = msg
        this.code = code
    }
}
