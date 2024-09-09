package kote.demo.app.baekjoon.repository

import java.util.*
import kote.demo.app.baekjoon.entity.AlgoType
import org.springframework.data.jpa.repository.JpaRepository

interface AlgoTypeRepository : JpaRepository<AlgoType, Long> {
  fun findByAlgoName(algoName: String): Optional<AlgoType>
}
