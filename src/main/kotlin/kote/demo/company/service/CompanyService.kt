package kote.demo.company.service

import kote.demo.company.dto.CompanyInfoDto
import kote.demo.company.entity.Company
import kote.demo.company.entity.CompanyProblem
import kote.demo.company.repository.CompanyProblemRepository
import kote.demo.company.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyService (
    private val companyRepository: CompanyRepository,
    private val companyProblemRepository:CompanyProblemRepository
){
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

        updateProblemLevel(problemAlgo.problemNumber, companyProblemList, problemAlgo.problemLevel.toFloat())
        updateProblemAlgo(problemAlgo.problemNumber, companyProblemList, problemAlgo.problemAlgo)

        companyProblemRepository.saveAll(companyProblemList)
    }

    fun updateProblemAlgo(
        number:Int,
        companyProblemList: MutableList<CompanyProblem>,
        algoName:String
    ){
        val map=stringToHashMap(companyProblemList[number-1].problemAlgo)
        if(map.containsKey(algoName)){
            map[algoName]=map.getValue(algoName)+4
        }
        else{
            map[algoName]=4
        }
        companyProblemList[number-1].problemAlgo=hashMapToString(map)

        if(number>1) {
            val secondMap = stringToHashMap(companyProblemList[number - 2].problemAlgo)
            if (secondMap.containsKey(algoName)) {
                secondMap[algoName] = secondMap.getValue(algoName) + 1
            } else {
                secondMap[algoName] = 1
            }
            companyProblemList[number-2].problemAlgo=hashMapToString(secondMap)
        }

        if(number<companyProblemList.size) {
            val thirdMap = stringToHashMap(companyProblemList[number].problemAlgo)
            if (thirdMap.containsKey(algoName)) {
                thirdMap[algoName] = thirdMap.getValue(algoName) + 1
            } else {
                thirdMap[algoName] = 1
            }
            companyProblemList[number].problemAlgo=hashMapToString(thirdMap)
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