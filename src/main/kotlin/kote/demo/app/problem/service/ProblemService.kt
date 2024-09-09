package kote.demo.app.problem.service

import kote.demo.app.baekjoon.service.BaekjoonProblemService
import kote.demo.app.company.entity.Company
import kote.demo.app.company.entity.CompanyProblem
import kote.demo.app.company.service.CompanyService
import kote.demo.app.problem.dto.ProblemDto
import kote.demo.app.problem.util.enum.AlgoWeightEnum
import kote.demo.common.exception.company.CompanyNotFoundException
import kote.demo.common.exception.problem.AlgorithmChooseException
import kote.demo.common.exception.problem.ProblemNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random
@Service
class ProblemService (
    private val companyService: CompanyService,
    private val baekjoonProblemService: BaekjoonProblemService,
){
    companion object{
        const val LEVEL_EASY = "easy"
        const val LEVEL_HARD = "hard"
        private val logger = LoggerFactory.getLogger(ProblemService::class.java)
    }
    fun getRandomTestList(
        companyName: String,
        difficulty: String,
    ): List<ProblemDto.BaekjoonProblemResponse> {
        val company = companyService.getCompanyByName(companyName) ?: throw CompanyNotFoundException()
        companyService.updateCompanyView(company)

        logger.info("get ${company.name}'s Problem Info")
        val companyAlgoExpectation= makeCompanyExpectTest(company)
        // 유저 난이도 반영
        adjustWithDifficulty(companyAlgoExpectation, difficulty)

        return matchWithBaekjoonProblem(companyAlgoExpectation)
    }

    fun adjustWithDifficulty(
        companyAlgoExpectation:List<ProblemDto.ProblemInfo>,
        difficulty: String
    ){
        when(difficulty){
            LEVEL_EASY -> companyAlgoExpectation.forEach{
                if(it.problemLevel>5){
                    it.problemLevel-=1
                }
            }
            LEVEL_HARD -> companyAlgoExpectation.forEach{
                if(it.problemLevel<16){
                    it.problemLevel+=1
                }
            }
        }
    }

    fun matchWithBaekjoonProblem(
        companyAlgoExpectation:List<ProblemDto.ProblemInfo>
    ): List<ProblemDto.BaekjoonProblemResponse> {
        val finalProblemInfoList= mutableListOf<ProblemDto.BaekjoonProblemResponse>()
        companyAlgoExpectation.forEach{
            val selectedTwoProblem=baekjoonProblemService.matchBaekjoon(it.problemLevel, it.problemAlgorithm)
            finalProblemInfoList.add(
                ProblemDto.revertBaekjoonProblemDto(selectedTwoProblem)
            )
        }
        return finalProblemInfoList
    }

    fun makeCompanyExpectTest(company:Company):List<ProblemDto.ProblemInfo>{
        val problemList=mutableListOf<ProblemDto.ProblemInfo>()

        problemList.add(
            chooseFirstProblemInfo(company.companyProblems?.find{
                it.problemNumber==1
            })
        )

        for(i in 2..company.problemNumber){
            problemList.add(
                chooseNextAlgorithmBasedOnHistory(problemList, company.companyProblems?.find{
                    it.problemNumber==i
                }?:throw ProblemNotFoundException())
            )
        }
        return problemList
    }

    fun chooseRandomAlgorithm(selectableProblemAlgoMap:HashMap<String,Int>):String{
        try{
            val totalWeight = selectableProblemAlgoMap.values.sum()
            val randomValue = Random.nextInt(totalWeight)

            var cumulativeWeight = 0
            for ((element, weight) in selectableProblemAlgoMap) {
                cumulativeWeight += weight
                if (randomValue < cumulativeWeight) {
                    return element
                }
            }

            throw AlgorithmChooseException()
        }catch (e:Exception){
            throw AlgorithmChooseException()
        }
    }

    fun chooseRandomLevel(problemExceptLevel:Float):Int{
        val upPercent=problemExceptLevel-problemExceptLevel.toInt()
        return if(Random.nextFloat()<=upPercent) problemExceptLevel.toInt()+1 else problemExceptLevel.toInt()
    }

    fun chooseFirstProblemInfo(companyProblem: CompanyProblem?):ProblemDto.ProblemInfo{

        val problemLevel= companyProblem?.problemLevel ?: throw ProblemNotFoundException()
        val problemAlgoMap=companyService.stringToHashMap(companyProblem.problemAlgo)

        val confirmLevel=chooseRandomLevel(problemLevel)
        val confirmAlgorithm = chooseRandomAlgorithm(problemAlgoMap)

        return ProblemDto.ProblemInfo(
            problemAlgorithm = confirmAlgorithm,
            problemLevel = confirmLevel,
        )
    }

    fun chooseNextAlgorithmBasedOnHistory(
        beforeProblemList:MutableList<ProblemDto.ProblemInfo>,
        companyProblem: CompanyProblem?
    ):ProblemDto.ProblemInfo{
        val problemLevel= companyProblem?.problemLevel ?: throw ProblemNotFoundException()
        val beforeChosenAlgo=beforeProblemList.map{
            it.problemAlgorithm
        }

        // 이전 문제에 사용된 알고리즘은 제외
        val problemAlgoMap=companyService.stringToHashMap(companyProblem.problemAlgo)
        beforeChosenAlgo.forEach{
            if(problemAlgoMap.containsKey(it)){
                problemAlgoMap.remove(it)
            }
        }

        val confirmLevel = chooseRandomLevel(problemLevel)
        val confirmAlgorithm = if(problemAlgoMap.isEmpty()){
            val algoList= AlgoWeightEnum.getEnumList()
            val selectableMap=algoList.filterNot{
                beforeChosenAlgo.contains(it.name)
            }
            AlgoWeightEnum.getRandomEnumByWeight(selectableMap)
        }
        else{
            chooseRandomAlgorithm(problemAlgoMap)
        }

        return ProblemDto.ProblemInfo(
            problemAlgorithm = confirmAlgorithm,
            problemLevel = confirmLevel
        )
    }

}