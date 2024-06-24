package kote.demo.company.entity

import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
class Company (
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
    var name:String,
    var problemNumber:Int,

    @OneToMany(mappedBy = "company")
    var companyProblems:MutableList<ProblemPrefer>?= mutableListOf(),
)