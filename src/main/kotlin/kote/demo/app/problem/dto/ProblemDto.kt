package kote.demo.app.problem.dto

import kote.demo.app.baekjoon.entity.BaekjoonProblem
import kote.demo.app.company.util.RatingEnum

class ProblemDto {
    data class ProblemInfo(
        var problemLevel:Int,
        var problemAlgorithm:String,
    )

    data class BaekjoonProblemResponse(
        var mainProblemName:String,
        var mainProblemLevel:String,
        var mainProblemAlgo:String,
        var mainProblemLink:String,

        var subProblemName:String,
        var subProblemLevel:String,
        var subProblemAlgo:String,
        var subProblemLink:String,
    )

    companion object {
        fun revertBaekjoonProblemDto(selectedProblem: List<BaekjoonProblem>): BaekjoonProblemResponse {
            return BaekjoonProblemResponse(
                mainProblemAlgo = selectedProblem[0].problemType.algoName ?: "",
                mainProblemLevel = RatingEnum.fromLevel(selectedProblem[0].problemTier).toString(),
                mainProblemName = selectedProblem[0].problemName,
                mainProblemLink = selectedProblem[0].problemLink,

                subProblemAlgo = selectedProblem[1].problemType.algoName ?: "",
                subProblemLevel = RatingEnum.fromLevel(selectedProblem[1].problemTier).toString(),
                subProblemName = selectedProblem[1].problemName,
                subProblemLink = selectedProblem[1].problemLink
            )
        }
    }
}