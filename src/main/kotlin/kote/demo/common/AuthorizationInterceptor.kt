package kote.demo.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthorizationInterceptor:HandlerInterceptor {
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
                response.sendRedirect("/login")
                return false
            }
        }

        return true
    }
}