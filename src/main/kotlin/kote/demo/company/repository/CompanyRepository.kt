package kote.demo.company.repository

import kote.demo.company.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CompanyRepository:JpaRepository<Company,Long>{
    @Query("SELECT mc FROM Company mc JOIN FETCH mc.companyProblems WHERE mc.id = :id")
    fun getCompanyWithProblems(id:Long):Company
}