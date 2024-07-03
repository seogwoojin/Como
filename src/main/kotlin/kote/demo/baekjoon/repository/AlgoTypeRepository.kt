package kote.demo.baekjoon.repository

import kote.demo.baekjoon.entity.AlgoType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AlgoTypeRepository: JpaRepository<AlgoType, Long>{
    fun findByAlgoName(algoName:String): Optional<AlgoType>
}