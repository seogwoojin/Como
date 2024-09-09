package kote.demo.app.baekjoon.repository

import kote.demo.app.baekjoon.entity.BaekjoonProblem
import org.springframework.data.jpa.repository.JpaRepository

interface BaekjoonProblemRepository : JpaRepository<BaekjoonProblem, Long> {
  fun findByProblemLink(link: String): BaekjoonProblem?
}
