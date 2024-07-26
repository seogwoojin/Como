package kote.demo.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthorizationInterceptor:HandlerInterceptor {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthorizationInterceptor::class.java)
    }
    override fun preHandle(
        request:HttpServletRequest,
        response:HttpServletResponse,
        handler:Any
    ):Boolean{
        val requestURI=request.requestURI
        // /admin 경로에 대한 접근 제한
        if (requestURI.startsWith("/company") || requestURI.startsWith("/baekjoon")) {
            val userRole = request.session.getAttribute("USER_ROLE") as String?

            if (userRole == null || userRole != "ADMIN") {
                logger.info("미인가 사용자 요청입니다")
                response.sendRedirect("/login?redirectURL=${requestURI}")
                return false
            }
        }

        return true
    }
}