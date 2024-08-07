package kote.demo.problem.dto

class ProblemRequestDto {
    data class ProblemRequest(
        val company:String,
        val choice:String,
    )
}