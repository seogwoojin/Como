package kote.demo.company.entity

import jakarta.persistence.*
import kote.demo.company.util.StringListConverter

@Entity
class ProblemPrefer (
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var company:Company,
    var problemNumber: Int,
    var preference:String?=null,


)

