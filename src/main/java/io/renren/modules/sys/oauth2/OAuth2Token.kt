/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2


import org.apache.shiro.authc.AuthenticationToken

/**
 * token
 *
 * @author Mark sunlightcs@gmail.com
 */
class OAuth2Token(private val token: String) : AuthenticationToken {

    @Override
    override fun getPrincipal(): String {
        return token
    }

    @Override
    override fun getCredentials(): Any {
        return token
    }
}
