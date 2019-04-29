package io.renren

import io.renren.common.utils.RedisUtils
import io.renren.modules.sys.entity.SysUserEntity
import org.apache.commons.lang.builder.ToStringBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class RedisTest {
    @Autowired
    private val redisUtils: RedisUtils? = null

    @Test
    fun contextLoads() {
        val user = SysUserEntity()
        user.email = "qqq@qq.com"
        redisUtils!!.set("user", user)

        System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", SysUserEntity::class.java)))
    }

}
