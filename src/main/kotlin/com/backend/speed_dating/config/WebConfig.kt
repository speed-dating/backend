package com.backend.speed_dating.config

import com.backend.speed_dating.common.resolver.UserTokenArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig (
    private val userTokenArgumentResolver: UserTokenArgumentResolver,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userTokenArgumentResolver)
    }
}