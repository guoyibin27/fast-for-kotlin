package io.renren

import io.renren.modules.app.utils.JwtUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class JwtTest {

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Test
    fun test() {
        val token = jwtUtils.generateToken(1)

        System.out.println(token)
    }

}
