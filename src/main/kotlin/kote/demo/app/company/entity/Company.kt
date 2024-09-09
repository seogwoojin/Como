package kote.demo.app.company.entity

import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
class Company (
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
    var name:String,
    var problemNumber:Int,
    var companyViews:Int,
    var companyHistoryUrl:String?="",
    var companyImageUrl:String?=null,

    @OneToMany(mappedBy = "company")
    var companyProblems:MutableList<CompanyProblem>?= mutableListOf(),
)