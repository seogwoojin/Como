package kote.demo.app.baekjoon.service

import kote.demo.app.baekjoon.entity.BaekjoonProblem
import kote.demo.app.baekjoon.repository.AlgoTypeRepository
import kote.demo.app.baekjoon.repository.BaekjoonProblemRepository
import kote.demo.common.exception.problem.ProblemNotFoundException
import kotlin.collections.ArrayDeque
import kotlin.random.Random
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BaekjoonProblemService(
    private val baekjoonProblemRepository: BaekjoonProblemRepository,
    private val algoTypeRepository: AlgoTypeRepository,
) {
  companion object {
    private val logger = LoggerFactory.getLogger(BaekjoonProblemService::class.java)
  }

  fun matchBaekjoon(level: Int, algorithm: String): List<BaekjoonProblem> {
    val levelQueue = ArrayDeque<Int>()
    levelQueue.add(level)
    val selectProblemList = mutableListOf<BaekjoonProblem>()
    val checkList: MutableList<Int> = MutableList(17) { 0 }
    val problemList = algoTypeRepository.findByAlgoName(algorithm).get().problemlists
    while (levelQueue.isNotEmpty() && selectProblemList.size < 2) {
      val checkingLevel = levelQueue.removeFirst()
      checkList[checkingLevel] = 1

      val problemListAfterLevel = problemList.filter { it.problemTier == checkingLevel }

      if (problemListAfterLevel.isEmpty()) {
        continue
      } else if (problemListAfterLevel.size == 1) {
        selectProblemList.add(problemListAfterLevel[0])
      } else {
        fillProblemApplyWeight(selectProblemList, problemListAfterLevel)
      }

      if (checkingLevel < 16 && checkList[checkingLevel + 1] == 0) levelQueue.add(checkingLevel + 1)
      if (checkingLevel > 5 && checkList[checkingLevel - 1] == 0) levelQueue.add(checkingLevel - 1)
    }
    selectProblemList.forEach {
      logger.info("complete selecting Problem ${it.problemName}:${it.problemTier}")
    }
    return if (selectProblemList.size == 2) selectProblemList else throw ProblemNotFoundException()
  }

  fun fillProblemApplyWeight(
      selectProblemList: MutableList<BaekjoonProblem>,
      problemList: List<BaekjoonProblem>
  ) {
    val highRateProblem = mutableListOf<Int>()
    val middleRateProblem = mutableListOf<Int>()
    val rowRateProblem = mutableListOf<Int>()
    for (i in 1..problemList.size) {
      val problemTry = problemList[i - 1].problemTry
      if (problemTry > 1500) highRateProblem.add(i - 1)
      else if (problemTry > 500) middleRateProblem.add(i - 1) else rowRateProblem.add(i - 1)
    }
    while (selectProblemList.size != 2) {
      val number = Random.nextInt(100)
      if (number < 10 && rowRateProblem.size > 0) {
        val randomIndex = Random.nextInt(rowRateProblem.size)
        if (!selectProblemList
            .map { it.problemLink }
            .contains(problemList[rowRateProblem[randomIndex]].problemLink)) {
          selectProblemList.add(problemList[rowRateProblem[randomIndex]])
        }
        rowRateProblem.removeAt(randomIndex)
      } else if (number < 30 && middleRateProblem.size > 0) {
        val randomIndex = Random.nextInt(middleRateProblem.size)
        if (!selectProblemList
            .map { it.problemLink }
            .contains(problemList[middleRateProblem[randomIndex]].problemLink)) {
          selectProblemList.add(problemList[middleRateProblem[randomIndex]])
        }
        middleRateProblem.removeAt(randomIndex)
      } else if (highRateProblem.size > 0) {
        val randomIndex = Random.nextInt(highRateProblem.size)
        if (!selectProblemList
            .map { it.problemLink }
            .contains(problemList[highRateProblem[randomIndex]].problemLink)) {
          selectProblemList.add(problemList[highRateProblem[randomIndex]])
        }
        highRateProblem.removeAt(randomIndex)
      }
    }
  }
}
