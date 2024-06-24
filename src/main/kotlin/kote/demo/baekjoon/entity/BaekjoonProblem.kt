package kote.demo.baekjoon.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class BaekjoonProblem (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var problemName:String,
    var problemLink:String,
    var problemTier:Int,
    var problemTry:Int,
)