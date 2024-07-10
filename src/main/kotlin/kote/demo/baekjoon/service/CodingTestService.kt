package kote.demo.baekjoon.service

import kote.demo.baekjoon.entity.AlgoType
import kote.demo.baekjoon.entity.BaekjoonProblem
import kote.demo.baekjoon.entity.enums.AlgorithmName
import kote.demo.baekjoon.repository.AlgoTypeRepository
import kote.demo.baekjoon.repository.BaekjoonProblemRepository
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URI
import java.util.*
import javax.swing.text.html.Option
import kotlin.collections.ArrayDeque
import kotlin.random.Random

@Service
class CodingTestService (
    private val baekjoonProblemRepository: BaekjoonProblemRepository,
    private val algoTypeRepository: AlgoTypeRepository,
) {

    fun matchBaekjoon(level: Int, algorithm: String): List<BaekjoonProblem> {
        val levelQueue = ArrayDeque<Int>()
        levelQueue.add(level)
        val selectProblemList = mutableListOf<BaekjoonProblem>()
        val checkList: MutableList<Int> = MutableList(17) { 0 }
        val problemList = algoTypeRepository.findByAlgoName(algorithm).get().problemlists
        while (levelQueue.isNotEmpty() && selectProblemList.size < 2) {
            val checkingLevel=levelQueue.removeFirst()
            checkList[checkingLevel]=1

            val problemListAfterLevel = problemList.filter {
                it.problemTier == checkingLevel
            }

            if (problemListAfterLevel.isEmpty()) {

            } else if (problemListAfterLevel.size == 1) {
                selectProblemList.add(problemListAfterLevel[0])
            } else {
                fillProblemApplyWeight(selectProblemList, problemListAfterLevel)
            }
            if (checkingLevel < 16 && checkList[checkingLevel+1]==0) levelQueue.add(checkingLevel + 1)
            if (checkingLevel > 5 && checkList[checkingLevel-1]==0) levelQueue.add(checkingLevel - 1)
        }
        selectProblemList.forEach {
            println(it.problemName + it.problemTier)
        }
        return if (selectProblemList.size == 2) selectProblemList else throw Exception()
    }

    fun fillProblemApplyWeight(selectProblemList: MutableList<BaekjoonProblem>, problemList: List<BaekjoonProblem>) {
        println("problemsize=   "+problemList.size)
        val highRateProblem = mutableListOf<Int>()
        val middleRateProblem = mutableListOf<Int>()
        val rowRateProblem = mutableListOf<Int>()
        for (i in 1..problemList.size) {
            val problemTry = problemList[i - 1].problemTry
            if (problemTry > 1500) highRateProblem.add(i - 1)
            else if (problemTry > 500) middleRateProblem.add(i - 1)
            else rowRateProblem.add(i - 1)
        }
        while (selectProblemList.size != 2) {
            val number = Random.nextInt(100)
            if (number < 10 && rowRateProblem.size > 0) {
                val randomIndex = Random.nextInt(rowRateProblem.size)
                if (!selectProblemList.map { it.problemLink }
                        .contains(problemList[rowRateProblem[randomIndex]].problemLink)) {
                    selectProblemList.add(
                        problemList[rowRateProblem[randomIndex]]
                    )
                }
                rowRateProblem.removeAt(randomIndex)
            } else if (number < 30 && middleRateProblem.size > 0) {
                val randomIndex = Random.nextInt(middleRateProblem.size)
                if (!selectProblemList.map { it.problemLink }
                        .contains(problemList[middleRateProblem[randomIndex]].problemLink)) {
                    selectProblemList.add(
                        problemList[middleRateProblem[randomIndex]]
                    )
                }
                middleRateProblem.removeAt(randomIndex)
            } else if (highRateProblem.size > 0) {
                val randomIndex = Random.nextInt(highRateProblem.size)
                if (!selectProblemList.map { it.problemLink }
                        .contains(problemList[highRateProblem[randomIndex]].problemLink)) {
                    selectProblemList.add(
                        problemList[highRateProblem[randomIndex]]
                    )
                }
                highRateProblem.removeAt(randomIndex)
            }
        }

    }

    fun getInfoFromBaekjoon(judge: String, autologin: String, order:Int) {
        val urlList: Array<String> = arrayOf(
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=25&algo_if=and",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=126%2C127&algo_if=or&",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=33&algo_if=and&page=1",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=141&algo_if=and&page=1", //시뮬레이션
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=125&algo_if=and&page=1", //브루트포스
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=158&algo_if=and&page=1", //문자열
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=12&algo_if=and&page=1",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=7&algo_if=and&page=1", //그래프 이론
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=175&algo_if=and&page=1", //자료구조
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=97&algo_if=and&page=1", //정렬
        )

        if(order==1){
            urlList.slice(0..1).forEach {
                saveProblemInfo(judge,autologin, it)
            }
        }

        else if(order==2){
            urlList.slice(2..3).forEach{
                saveProblemInfo(judge, autologin, it)
            }
        }

        else if(order==3){
            urlList.slice(4..6).forEach {
                saveProblemInfo(judge, autologin, it)
            }
        }

        else{
            urlList.slice(7..9).forEach {
                saveProblemInfo(judge, autologin, it)
            }
        }
    }

    fun saveProblemInfo(judge: String, autologin:String, it:String) {
        val algoId = extractFirstAlgoValue(it)
        val algoName: String?
        if (algoId != null) {
            algoName = AlgorithmName.fromId(algoId)
        } else {
            throw Exception()
        }
        val algoType: Optional<AlgoType>
        if (algoName != null) {
            algoType=algoTypeRepository.findByAlgoName(algoName)
        }
        else{
            algoType=Optional.empty()
        }
        val nowAlgoType:AlgoType
        if(algoType.isPresent){
            nowAlgoType=algoType.get()
        }
        else {
            nowAlgoType = AlgoType(
                algoId = algoId,
                algoName = algoName,
                problemlists = mutableListOf()
            )
            algoTypeRepository.save(nowAlgoType)
        }

        val pageNow = it
        var pageNumber = 1
        var shouldBreak = false

        while (!shouldBreak) {
            val page = "$pageNow&page=$pageNumber"
            println(page)
            val tbodyList = Jsoup.connect(page)
                .header("Cookie", "OnlineJudge=${judge}; bojautologin=${autologin}")
                .get()
                .getElementsByTag("tbody")

            // tbodyList가 비어있으면 루프를 종료합니다.
            if (tbodyList.isEmpty()) {
                break
            }

            tbodyList.select("tr").forEach { row ->
                if (shouldBreak) {
                    return@forEach
                }

                val tierSrc = row.select("td img").attr("src")
                val tier = tierSrc.substring(tierSrc.lastIndexOf('/') + 1, tierSrc.lastIndexOf('.')).toInt()
                val href = row.select("td a").first()!!.attr("href")
                val title = row.select("td a").first()!!.text()
                val tries = row.select("td a").last()!!.text().toInt()

                // 200회 이하 시도 문제가 나오면 전체 루프를 탈출합니다.
                if (tries < 200) {
                    shouldBreak = true
                    return@forEach
                }

                // 500회 이하이면서 한글이 아닌 문제 패스
                if (tries < 500 && !title.first().isHangul()) {
                    return@forEach
                }

                val baekjoonProblem = baekjoonProblemRepository.findByProblemLink(href)

                if (baekjoonProblem == null) {
                    baekjoonProblemRepository.save(
                        BaekjoonProblem(
                            problemLink = href,
                            problemName = title,
                            problemTier = tier,
                            problemTry = tries,
                            problemType = nowAlgoType
                        )
                    )
                }
            }

            pageNumber += 1
        }

    }

    fun Char.isHangul(): Boolean {
        return this in '\uAC00'..'\uD7A3'
    }

    fun extractFirstAlgoValue(url: String): Int? {
        val uri = URI(url)
        val queryParams = uri.query.split("&")
        val algoParam = queryParams.find { it.startsWith("algo=") } ?: return null
        val algoValues = algoParam.split("=")[1]

        return algoValues.split(",").firstOrNull()?.toInt()
    }
}