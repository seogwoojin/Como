package kote.demo.main.api

import jakarta.servlet.http.HttpServletRequest
import kote.demo.baekjoon.dto.TestResponseDto
import kote.demo.company.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("")
class MainController (
    private val companyService: CompanyService,
){
    val randomUUID:UUID=UUID.randomUUID()
    @GetMapping
    fun getMain(model: Model):String{
        model.addAttribute("items", companyService.getCompanyInfoList())
        return "main"
    }

    @GetMapping("/ping")
    fun test():ResponseEntity<UUID>{
        return ResponseEntity.ok(randomUUID)
    }
}