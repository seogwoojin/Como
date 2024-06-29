package kote.demo.company.api

import kote.demo.company.dto.CompanyInfoDto
import kote.demo.company.repository.CompanyRepository
import kote.demo.company.repository.CompanyProblemRepository
import kote.demo.company.service.CompanyService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/company")
class CompanyController (
    private val companyService: CompanyService,
){
    @GetMapping
    fun mainCompany():String{
        return "company/mainCompany"
    }

    @PostMapping()
    @ResponseBody
    fun makeCompany(
        @RequestBody companyInfo:CompanyInfoDto.CompanyRequest
    ){
       return companyService.saveNewCompany(companyInfo)
    }

    @GetMapping("/problem")
    fun mainCompanyProblem():String{
        return "company/mainCompanyProblem"
    }

    @PatchMapping
    fun updateCompanyProblem(
        @RequestBody problemAlgo: CompanyInfoDto.ProblemPreferRequestDto
    ){
        companyService.patchProblemPreference(problemAlgo)
    }
}