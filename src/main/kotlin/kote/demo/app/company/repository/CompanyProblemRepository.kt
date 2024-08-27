package kote.demo.app.company.repository

import kote.demo.app.company.entity.Company
import kote.demo.app.company.entity.CompanyProblem
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyProblemRepository : JpaRepository<CompanyProblem,Long>{
    fun findByCompanyAndProblemNumber(company: Company,problemNumber: Int):CompanyProblem

    fun findAllByCompanyOrderByProblemNumberAsc(company: Company):MutableList<CompanyProblem>
}