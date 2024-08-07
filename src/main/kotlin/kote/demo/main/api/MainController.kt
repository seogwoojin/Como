package kote.demo.main.api

import kote.demo.company.service.CompanyService
import kote.demo.main.SuggestionRepository
import kote.demo.main.entity.Suggestion
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("")
class MainController (
    private val companyService: CompanyService,
    private val suggestionRepository: SuggestionRepository,
) {
    @GetMapping
    fun getMain(model: Model): String {
        model.addAttribute("items", companyService.getCompanyInfoList())
        return "main"
    }

    @GetMapping("/contact")
    fun getContact():String{
        return "contact"
    }

    @PostMapping("/contact")
    fun receiveContact(
        @RequestParam name:String,
        @RequestParam inquiry:String,
        @RequestParam message:String,
        model: Model
    ):String{
        suggestionRepository.save(Suggestion(
            name=name,
            inquiry = inquiry,
            message = message
        ))
        model.addAttribute("successMessage", "$name 님, 소중한 의견 감사합니다!")
        return "contact"
    }
}