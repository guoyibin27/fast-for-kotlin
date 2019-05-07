package io.renren.modules.job.task

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import io.renren.modules.sys.entity.SysCaptchaEntity
import io.renren.modules.sys.service.SysCaptchaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component("captchaCleanTask")
class CaptchaCleanTask : ITask {

    @Autowired
    lateinit var sysCaptchaService: SysCaptchaService

    override fun run(params: String) {
        val now = Date()
        val wrapper = QueryWrapper<SysCaptchaEntity>()
        wrapper.le("expire_time", now)
        sysCaptchaService.remove(wrapper)
    }

}