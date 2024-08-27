package kote.demo.app.problem.service

import kote.demo.app.baekjoon.service.CodingTestService
import kote.demo.common.exception.baseexception.BusinessException
import kote.demo.app.company.entity.Company
import kote.demo.app.company.entity.CompanyProblem
import kote.demo.app.company.service.CompanyService
import kote.demo.app.problem.dto.ProblemDto
import kote.demo.app.problem.util.enum.AlgoWeightEnum
import kote.demo.common.exception.company.CompanyNotFoundException
import kote.demo.common.exception.problem.ProblemNotFoundException
import org.springframework.stereotype.Service
import kotlin.random.Random
@Service
class ProblemService (
    private val companyService: CompanyService,
    private val codingTestService: CodingTestService,
){
    fun getRandomTestList(
        companyName: String,
        difficulty: String,
    ): List<ProblemDto.BaekjoonProblemResponse> {
        val company = companyService.getCompanyByName(companyName) ?: throw CompanyNotFoundException()
        companyService.updateCompanyView(company)
        val companyAlgoExpectation= makeCompanyExpectTest(company)
        if(difficulty=="easy"){
            companyAlgoExpectation.forEach{
                if(it.problemLevel>5){
                    it.problemLevel-=1
                }
            }
        }
        if(difficulty=="hard"){
            companyAlgoExpectation.forEach{
                if(it.problemLevel<16){
                    it.problemLevel+=1
                }
            }
        }
        return mapBaekjoonProblem(companyAlgoExpectation)
    }

    fun mapBaekjoonProblem(companyAlgoExpectation:List<ProblemDto.ProblemInfo>):
            List<ProblemDto.BaekjoonProblemResponse> {
        val finalProblemInfoList= mutableListOf<ProblemDto.BaekjoonProblemResponse>()
        companyAlgoExpectation.forEach{
            val selectTwoProblem=codingTestService.matchBaekjoon(it.problemLevel, it.problemAlgorithm)
            finalProblemInfoList.add(
                ProblemDto.revertBaekjoonProblemDto(selectTwoProblem)
            )
        }
        return finalProblemInfoList
    }

    fun makeCompanyExpectTest(company:Company):List<ProblemDto.ProblemInfo>{
        val problemList=mutableListOf<ProblemDto.ProblemInfo>()

        problemList.add(
            makeFirstProblem(company.companyProblems?.find{
                it.problemNumber==1
            })
        )

        for(i in 2..company.problemNumber){
            problemList.add(
                chooseNextBasedOnHistory(problemList, company.companyProblems?.find{
                    it.problemNumber==i
                }?:throw ProblemNotFoundException())
            )
        }
        return problemList
    }
    fun chooseNextBasedOnHistory(
        beforeProblemList:MutableList<ProblemDto.ProblemInfo>,
        companyProblem: CompanyProblem
    ):ProblemDto.ProblemInfo{
        val beforeChooseAlgo=beforeProblemList.map{
            it.problemAlgorithm
        }
        val problemAlgoMap=companyService.stringToHashMap(companyProblem.problemAlgo)
        beforeChooseAlgo.forEach{
            if(problemAlgoMap.containsKey(it)){
                problemAlgoMap.remove(it)
            }
        }
        val problemAlgorithm=if(problemAlgoMap.isEmpty()){
            val algoList= AlgoWeightEnum.getEnumList()
            val selectAble=algoList.filterNot{
                beforeChooseAlgo.contains(it.name)
            }
            selectAble.forEach {
                println(it.name)
            }
            println("select random Problems->")
            AlgoWeightEnum.getRandomEnumByWeight(selectAble)
        }
        else{
            recommendAlgorithm(problemAlgoMap)
        }
        return ProblemDto.ProblemInfo(
            problemAlgorithm = problemAlgorithm,
            problemLevel = recommendLevel(companyProblem.problemLevel?: throw Exception())
        )
    }
    fun recommendAlgorithm(problemAlgoMap:HashMap<String,Int>):String{
        val totalWeight=problemAlgoMap.values.sum()
        val randomValue=Random.nextInt(totalWeight)

        var cumulativeWeight = 0
        for ((element, weight) in problemAlgoMap) {
            cumulativeWeight += weight
            if (randomValue < cumulativeWeight) {
                return element
            }
        }
        return ""
    }

    fun recommendLevel(problemLevel:Float):Int{
        val upPercent=problemLevel-problemLevel.toInt()
        return if(Random.nextFloat()<=upPercent) problemLevel.toInt()+1 else problemLevel.toInt()
    }

    fun makeFirstProblem(companyProblem: CompanyProblem?):ProblemDto.ProblemInfo{

        val problemLevel= companyProblem?.problemLevel ?: throw Exception()
        val confirmLevel=recommendLevel(problemLevel)
        val problemAlgoMap=companyService.stringToHashMap(companyProblem.problemAlgo)


        return ProblemDto.ProblemInfo(
            problemAlgorithm = recommendAlgorithm(problemAlgoMap),
            problemLevel = confirmLevel,
        )
    }

}