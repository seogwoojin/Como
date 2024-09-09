package kote.demo.app.baekjoon.repository

import kote.demo.app.baekjoon.entity.AlgoType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AlgoTypeRepository: JpaRepository<AlgoType, Long>{
    fun findByAlgoName(algoName:String): Optional<AlgoType>
}