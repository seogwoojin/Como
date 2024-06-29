package kote.demo.baekjoon.repository

import kote.demo.baekjoon.entity.BaekjoonProblem
import kote.demo.baekjoon.entity.ProblemAlgo
import org.springframework.data.jpa.repository.JpaRepository

interface ProblemAlgoRepository:JpaRepository<ProblemAlgo,Long>