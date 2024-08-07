package kote.demo.company.api

import kote.demo.company.dto.CompanyInfoDto
import kote.demo.company.repository.CompanyRepository
import kote.demo.company.repository.CompanyProblemRepository
import kote.demo.company.service.CompanyService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/company")
class CompanyController (
    private val companyService: CompanyService,
){
    @GetMapping
    fun mainCompany():String{
        return "company/mainCompany"
    }

    @PostMapping
    @ResponseBody
    fun makeCompany(
        @ModelAttribute companyInfo: CompanyInfoDto.CompanyRequest,
        @RequestParam("image") file:MultipartFile,
    ){
       return companyService.saveNewCompany(companyInfo, file)
    }

    @GetMapping("/problem")
    fun mainCompanyProblem(model: Model):String{
        model.addAttribute("companies",companyService.getCompanyList())
        return "company/mainCompanyProblem"
    }

    @PatchMapping
    fun updateCompanyProblem(
        @RequestBody problemAlgo: CompanyInfoDto.ProblemPreferRequestDto
    ){
        companyService.patchProblemPreference(problemAlgo)
    }

    @PostMapping("/links")
    @ResponseBody
    fun updateCompanyLinks(
        @RequestParam companyName: String,
        @RequestParam urls: String
    ):String{
        companyService.addCompanyLink(companyName,urls)
        return "OK"
    }
}