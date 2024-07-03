package kote.demo.baekjoon.repository

import kote.demo.baekjoon.entity.BaekjoonProblem
import org.springframework.data.jpa.repository.JpaRepository

interface BaekjoonProblemRepository:JpaRepository<BaekjoonProblem,Long>{
    fun findByProblemLink(link:String):BaekjoonProblem?
}