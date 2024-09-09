package kote.demo.app.problem.api

import kote.demo.app.company.service.CompanyService
import kote.demo.app.problem.dto.ProblemRequestDto
import kote.demo.app.problem.service.ProblemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/problem")
class ProblemController(
    private val problemService: ProblemService,
    private val companyService: CompanyService,
) {
  @PostMapping
  fun getTestList(
      @RequestBody problemRequest: ProblemRequestDto.ProblemRequest,
      model: Model
  ): String {
    val company = problemRequest.company
    val choice = problemRequest.choice
    model.addAttribute("company", company)
    model.addAttribute("choice", choice)
    model.addAttribute("links", companyService.getCompanyLinks(company))
    model.addAttribute("problems", problemService.getRandomTestList(company, choice))
    return "problem/problems"
  }
}
