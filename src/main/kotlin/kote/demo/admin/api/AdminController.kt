package kote.demo.admin.api

import jakarta.servlet.http.HttpServletRequest
import kote.demo.admin.service.AdminService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AdminController(
    private val adminService: AdminService,
){

    @GetMapping("/login")
    fun showLoginForm(): String {
        return "login/login-page"
    }

    @PostMapping("/login")
    fun login(request: HttpServletRequest): String {
        val username = request.getParameter("username")
        val password = request.getParameter("password")

        val user = adminService.findByUsername(username)

        return if (user != null && password==user.password ) {
            request.session.setAttribute("USER_ROLE", user.role)
            "redirect:/baekjoon"
        } else {
            "redirect:/login?error"
        }
    }
}