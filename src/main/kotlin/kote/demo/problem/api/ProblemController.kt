package kote.demo.problem.api

import kote.demo.problem.service.ProblemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/problem")
class ProblemController (
    private val problemService: ProblemService,
){
    @GetMapping
    fun getTestList(
        @RequestParam company:String,
        @RequestParam choice:String,
        model: Model
    ):String{
        model.addAttribute("company",company)
        model.addAttribute("choice",choice)
        model.addAttribute("problems",problemService.getRandomTestList(company,choice))
        return "problem/problems"
    }
}