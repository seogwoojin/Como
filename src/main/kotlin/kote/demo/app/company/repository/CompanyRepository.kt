package kote.demo.app.company.repository

import kote.demo.app.company.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CompanyRepository : JpaRepository<Company, Long> {
  @Query("SELECT mc FROM Company mc JOIN FETCH mc.companyProblems WHERE mc.name = :name")
  fun getCompanyWithProblems(name: String): Company

  fun findByName(name: String): Company?
}
