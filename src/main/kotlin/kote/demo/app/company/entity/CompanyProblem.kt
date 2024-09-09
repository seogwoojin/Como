package kote.demo.app.company.entity

import jakarta.persistence.*

@Entity
class CompanyProblem(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
    @ManyToOne @JoinColumn(name = "company_id") var company: Company,
    var problemNumber: Int,
    var problemAlgo: String,
    var problemLevel: Float? = null,
)
