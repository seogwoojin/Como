package kote.demo.company.repository

import kote.demo.company.entity.Company
import kote.demo.company.entity.CompanyProblem
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyProblemRepository : JpaRepository<CompanyProblem,Long>{
    fun findByCompanyAndProblemNumber(company: Company,problemNumber: Int):CompanyProblem

    fun findAllByCompanyOrderByProblemNumberAsc(company: Company):MutableList<CompanyProblem>
}