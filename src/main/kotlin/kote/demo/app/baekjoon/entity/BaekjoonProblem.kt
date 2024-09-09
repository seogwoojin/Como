package kote.demo.app.baekjoon.entity

import jakarta.persistence.*

@Entity
class BaekjoonProblem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var problemName: String,
    var problemLink: String,
    var problemTier: Int,
    var problemTry: Int,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "algoType_id") var problemType: AlgoType,
)
