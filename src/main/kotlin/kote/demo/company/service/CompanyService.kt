package kote.demo.company.service

import kote.demo.company.dto.CompanyInfoDto
import kote.demo.company.entity.Company
import kote.demo.company.entity.CompanyProblem
import kote.demo.company.repository.CompanyProblemRepository
import kote.demo.company.repository.CompanyRepository
import kote.demo.company.util.RatingEnum
import org.springframework.stereotype.Service

@Service
class CompanyService (
    private val companyRepository: CompanyRepository,
    private val companyProblemRepository:CompanyProblemRepository
){
    fun getCompanyByName(name:String):Company?{
        return companyRepository.getCompanyWithProblems(name)
    }
    fun getCompanyList():List<String>{
        return companyRepository.findAll().map{
            it.name
        }
    }
    fun getCompanyInfoList():List<CompanyInfoDto.CompanyMainInfoDto>{
        val companyMainInfoList= mutableListOf<CompanyInfoDto.CompanyMainInfoDto>()
        companyRepository.findAll().forEach{
            companyMainInfoList.add(
                CompanyInfoDto.CompanyMainInfoDto(
                    companyImg="/images/사피.png",
                    companyClick = 5,
                    companyName = it.name
                )
            )
        }
        return companyMainInfoList
    }
    fun saveNewCompany(companyInfo: CompanyInfoDto.CompanyRequest){
        val existingCompany=companyRepository.findByName(companyInfo.companyName)

        if (existingCompany != null) {
//            throw CompanyAlreadyExistsException("Company '${companyInfo.companyName}' already exists")
            throw Exception("already exist")
        }
        val company= Company(
            name=companyInfo.companyName,
            problemNumber = companyInfo.problemCount
        )

        companyRepository.save(company)

        val companyProblemList= mutableListOf<CompanyProblem>()
        for (num in 1..companyInfo.problemCount){
            companyProblemList.add(
                CompanyProblem(
                    company=company,
                    problemNumber = num,
                    problemAlgo = ""
                )
            )
        }
        companyProblemRepository.saveAll(companyProblemList)
    }

    fun patchProblemPreference(problemAlgo:CompanyInfoDto.ProblemPreferRequestDto){
        val company=companyRepository.findByName(problemAlgo.companyName)?:throw Exception()
        val companyProblemList=companyProblemRepository.findAllByCompanyOrderByProblemNumberAsc(company)

        updateProblemLevel(problemAlgo.problemNumber, companyProblemList, RatingEnum.valueOf(problemAlgo.problemLevel).level.toFloat())
        updateProblemAlgo(problemAlgo.problemNumber, companyProblemList, problemAlgo.problemAlgo)

        companyProblemRepository.saveAll(companyProblemList)
    }

    fun updateProblemAlgo(
        number:Int,
        companyProblemList: MutableList<CompanyProblem>,
        algoName:String
    ){
        val map=stringToHashMap(companyProblemList[number-1].problemAlgo)

        for(i in 1..companyProblemList.size){
            val map=stringToHashMap(companyProblemList[i-1].problemAlgo)
            if(i==number){
                if(map.containsKey(algoName)){
                    map[algoName]=map.getValue(algoName)+5
                }
                else{
                    map[algoName]=5
                }
            }
            else{
                if(map.containsKey(algoName)){
                    map[algoName]=map.getValue(algoName)+1
                }
                else{
                    map[algoName]=1
                }
            }
            companyProblemList[i-1].problemAlgo=hashMapToString(map)
        }
    }

    // 문자열을 해시맵으로 변환하는 함수
    fun stringToHashMap(input: String): HashMap<String, Int> {
        val map = HashMap<String, Int>()
        val pairs = input.split(",")

        for (pair in pairs) {
            val keyValue = pair.split(":")
            if (keyValue.size == 2) {
                val (key, value) = keyValue
                map[key] = value.toIntOrNull() ?: 0
            }
        }

        return map
    }

    // 해시맵을 문자열로 변환하는 함수
    fun hashMapToString(map: HashMap<String, Int>): String {
        val pairs = map.entries.joinToString(",") { "${it.key}:${it.value}" }
        return pairs
    }

    fun updateProblemLevel(
        number:Int,
        companyProblemList:MutableList<CompanyProblem>,
        level:Float
    ){
        if(companyProblemList[number-1].problemLevel==null){
            companyProblemList[number-1].problemLevel=level
        }
        else{
            companyProblemList[number-1].problemLevel=
                (companyProblemList[number-1].problemLevel!! + level)/2
        }

        if(number>1) {
            if (companyProblemList[number - 2].problemLevel != null) {
                companyProblemList[number-2].problemLevel = (companyProblemList[number-2].problemLevel!! + 0.1f*level) / 1.1f
            }
        }

        if(number<companyProblemList.size){
            if(companyProblemList[number].problemLevel != null){
                companyProblemList[number].problemLevel=(companyProblemList[number].problemLevel!! + 0.1f*level) / 1.1f
            }
        }
    }
}