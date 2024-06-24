package kote.demo.company.api

import kote.demo.company.entity.Company
import kote.demo.company.entity.ProblemPrefer
import kote.demo.company.repository.CompanyRepository
import kote.demo.company.repository.ProblemPreferRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/company")
class CompanyController (
    private val companyRepository: CompanyRepository,
    private val problemPreferRepository: ProblemPreferRepository
){
    @GetMapping
    fun makeCompany(){
        val dummy= Company(
            name="소마",
            problemNumber = 5,
        )
        val plist= mutableListOf<ProblemPrefer>()
        plist.add(
            ProblemPrefer(
                company = dummy,
                problemNumber = 3,
                preference = "그래프333:0.5"
            )
        )
        plist.add(
            ProblemPrefer(
                company = dummy,
                problemNumber = 4,
                preference = "dp444:0.5"
            )
        )
        companyRepository.save(dummy)

        problemPreferRepository.saveAll(plist)
        val pl=problemPreferRepository.findAll()

//
        val c=companyRepository.findById(1L)
//        println(c.get().companyProblems)
//        c.get().companyProblems?.forEach {
//            println(it.company.name)
//        }
//        println(c.get().name)
//        println(c.companyProblems.toString())

    }

}