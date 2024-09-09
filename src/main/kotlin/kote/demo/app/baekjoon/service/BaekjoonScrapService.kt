package kote.demo.app.baekjoon.service

import java.net.URI
import java.util.*
import kote.demo.app.baekjoon.entity.AlgoType
import kote.demo.app.baekjoon.entity.BaekjoonProblem
import kote.demo.app.baekjoon.entity.enums.AlgorithmName
import kote.demo.app.baekjoon.repository.AlgoTypeRepository
import kote.demo.app.baekjoon.repository.BaekjoonProblemRepository
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class BaekjoonScrapService(
    private val baekjoonProblemRepository: BaekjoonProblemRepository,
    private val algoTypeRepository: AlgoTypeRepository,
) {
  fun getInfoFromBaekjoon(judge: String, autologin: String, order: Int) {
    val urlList: Array<String> =
        arrayOf(
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=25&algo_if=and",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=126%2C127&algo_if=or&",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=33&algo_if=and&page=1",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=141&algo_if=and&page=1", // 시뮬레이션
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=125&algo_if=and&page=1", // 브루트포스
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=158&algo_if=and&page=1", // 문자열
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=12&algo_if=and&page=1",
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=7&algo_if=and&page=1", // 그래프 이론
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=175&algo_if=and&page=1", // 자료구조
            "https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16&algo=97&algo_if=and&page=1", // 정렬
        )

    if (order == 1) {
      urlList.slice(0..1).forEach { saveProblemInfo(judge, autologin, it) }
    } else if (order == 2) {
      urlList.slice(2..3).forEach { saveProblemInfo(judge, autologin, it) }
    } else if (order == 3) {
      urlList.slice(4..6).forEach { saveProblemInfo(judge, autologin, it) }
    } else {
      urlList.slice(7..9).forEach { saveProblemInfo(judge, autologin, it) }
    }
  }

  fun saveProblemInfo(judge: String, autologin: String, it: String) {
    val algoId = extractFirstAlgoValue(it)
    val algoName: String?
    if (algoId != null) {
      algoName = AlgorithmName.fromId(algoId)
    } else {
      throw Exception()
    }
    val algoType: Optional<AlgoType>
    if (algoName != null) {
      algoType = algoTypeRepository.findByAlgoName(algoName)
    } else {
      algoType = Optional.empty()
    }
    val nowAlgoType: AlgoType
    if (algoType.isPresent) {
      nowAlgoType = algoType.get()
    } else {
      nowAlgoType = AlgoType(algoId = algoId, algoName = algoName, problemlists = mutableListOf())
      algoTypeRepository.save(nowAlgoType)
    }

    val pageNow = it
    var pageNumber = 1
    var shouldBreak = false

    while (!shouldBreak) {
      val page = "$pageNow&page=$pageNumber"
      val tbodyList =
          Jsoup.connect(page)
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
                  problemType = nowAlgoType))
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
