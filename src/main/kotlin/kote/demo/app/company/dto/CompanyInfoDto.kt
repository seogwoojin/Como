package kote.demo.app.company.dto

class CompanyInfoDto {
    data class CompanyRequest(
        val companyName: String,
        val problemCount: Int
    )

    data class ProblemPreferRequestDto(
        val companyName: String,
        val problemNumber:Int,
        val problemLevel:String,
        val problemAlgo:String,
    )

    data class CompanyUrlRequest(
        val companyName: String,
        val urls:String
    )

    data class CompanyMainInfoDto(
        val companyImg:String,
        val companyClick:Int,
        val companyName:String,
    )
}