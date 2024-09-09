package kote.demo.app.admin.api

import jakarta.servlet.http.HttpServletRequest
import kote.demo.app.admin.service.AdminService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class AdminController(
    private val adminService: AdminService,
) {

  @GetMapping("/login")
  fun showLoginForm(model: Model, @RequestParam redirectURL: String): String {
    model.addAttribute("redirectURL", redirectURL)
    return "login/login-page"
  }

  @PostMapping("/login")
  fun login(request: HttpServletRequest, @RequestParam redirectURL: String): String {
    val username = request.getParameter("username")
    val password = request.getParameter("password")

    val user = adminService.findByUsername(username)

    return if (user != null && password == user.password) {
      request.session.setAttribute("USER_ROLE", user.role)
      "redirect:${redirectURL}"
    } else {
      "redirect:/login?error"
    }
  }
}
