package kote.demo.common.config

import kote.demo.common.AuthorizationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(private val authorizationInterceptor: AuthorizationInterceptor) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authorizationInterceptor)
            .addPathPatterns("/baekjoon/**", "/company/**") // /admin 경로에 대한 인터셉터 적용
            .excludePathPatterns("**") // /login과 /public 경로는 제외
    }
}