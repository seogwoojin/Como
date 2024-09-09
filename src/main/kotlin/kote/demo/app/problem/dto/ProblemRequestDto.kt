package kote.demo.app.problem.dto

class ProblemRequestDto {
  data class ProblemRequest(
      val company: String,
      val choice: String,
  )
}
