package kote.demo.baekjoon.repository

import kote.demo.baekjoon.entity.AlgoType
import org.springframework.data.jpa.repository.JpaRepository

interface AlgoTypeRepository: JpaRepository<AlgoType, Long>{
    fun findByAlgoName(algoName:String):AlgoType
}