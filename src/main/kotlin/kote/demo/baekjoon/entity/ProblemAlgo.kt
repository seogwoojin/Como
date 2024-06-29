package kote.demo.baekjoon.entity

import jakarta.persistence.*

@Entity
class ProblemAlgo (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="baekjoonProblem_id")
    var baekjoonProblem: BaekjoonProblem,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="algoType_id")
    var algoType: AlgoType
)
