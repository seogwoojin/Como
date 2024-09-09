package kote.demo.app.baekjoon.entity

import jakarta.persistence.*

@Entity
class AlgoType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var algoId: Int,
    var algoName: String? = null,
    @OneToMany(mappedBy = "problemType") var problemlists: MutableList<BaekjoonProblem>,
)
