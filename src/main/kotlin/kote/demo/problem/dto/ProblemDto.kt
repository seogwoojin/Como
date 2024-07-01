package kote.demo.problem.dto

import kote.demo.baekjoon.entity.BaekjoonProblem

class ProblemDto {
    data class ProblemInfo(
        var problemLevel:Int,
        var problemAlgorithm:String,
    )

    data class BaekjoonProblemResponse(
        var mainProblemName:String,
        var mainProblemLevel:Int,
        var mainProblemAlgo:String,
        var mainProblemLink:String,

        var subProblemName:String,
        var subProblemLevel:Int,
        var subProblemAlgo:String,
        var subProblemLink:String,
    )

    companion object {
        fun revertBaekjoonProblemDto(selectedProblem: List<BaekjoonProblem>): BaekjoonProblemResponse {
            return BaekjoonProblemResponse(
                mainProblemAlgo = selectedProblem[0].problemType.algoName ?: "",
                mainProblemLevel = selectedProblem[0].problemTier,
                mainProblemName = selectedProblem[0].problemName,
                mainProblemLink = selectedProblem[0].problemLink,

                subProblemAlgo = selectedProblem[1].problemType.algoName ?: "",
                subProblemLevel = selectedProblem[1].problemTier,
                subProblemName = selectedProblem[1].problemName,
                subProblemLink = selectedProblem[1].problemLink
            )
        }
    }
}