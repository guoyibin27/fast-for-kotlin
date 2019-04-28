/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.oauth2

import io.renren.common.exception.RRException
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.service.ShiroService
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * 认证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
class OAuth2Realm : AuthorizingRealm() {
    @Autowired
    lateinit var shiroService: ShiroService

    override fun supports(token: AuthenticationToken?): Boolean {
        return token is OAuth2Token
    }

    /**
     * 授权(验证权限时调用)
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo {
        val user = principals.primaryPrincipal as SysUserEntity
        val userId = user.userId

        //用户权限列表
        val permsSet = shiroService.getUserPermissions(userId!!.toLong())

        val info = SimpleAuthorizationInfo()
        info.stringPermissions = permsSet
        return info
    }

    /**
     * 认证(登录时调用)
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo {
        val accessToken = token.principal as String

        //根据accessToken，查询用户信息
        val tokenEntity = shiroService.queryByToken(accessToken)
        //token失效
        if (tokenEntity == null || tokenEntity.expireTime!!.time < System.currentTimeMillis()) {
            throw IncorrectCredentialsException("token失效，请重新登录")
        }

        //查询用户信息
        val user = shiroService.queryUser(tokenEntity.userId!!) ?: throw RRException("用户不存在")
        //账号锁定
        if (user.status == 0) {
            throw LockedAccountException("账号已被锁定,请联系管理员")
        }

        return SimpleAuthenticationInfo(user, accessToken, name)
    }
}
