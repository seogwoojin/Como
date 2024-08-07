package kote.demo.problem.api

import kote.demo.company.service.CompanyService
import kote.demo.problem.dto.ProblemRequestDto
import kote.demo.problem.service.ProblemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/problem")
class ProblemController (
    private val problemService: ProblemService,
    private val companyService: CompanyService,
){
    @PostMapping
    fun getTestList(
        @RequestBody problemRequest: ProblemRequestDto.ProblemRequest,
        model: Model
    ):String{
        val company=problemRequest.company
        val choice=problemRequest.choice
        model.addAttribute("company",company)
        model.addAttribute("choice",choice)
        model.addAttribute("links", companyService.getCompanyLinks(company))
        model.addAttribute("problems",problemService.getRandomTestList(company,choice))
        return "problem/problems"
    }
}