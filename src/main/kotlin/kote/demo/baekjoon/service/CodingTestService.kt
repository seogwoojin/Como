package kote.demo.baekjoon.service

import kote.demo.baekjoon.entity.BaekjoonProblem
import kote.demo.baekjoon.repository.BaekjoonProblemRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.stereotype.Service

@Service
class CodingTestService (
    private val baekjoonProblemRepository: BaekjoonProblemRepository
){
    fun fetchPage(url: String): Document {
        return Jsoup.connect(url).get()
    }

    fun parsePage(document: Document): Map<String, String> {
        val title = document.title()
        val body = document.body().text()
        return mapOf("title" to title, "body" to body)
    }

    fun parseHtml(url: String) {
//        val res=Jsoup.connect("https://www.acmicpc.net/login?next=%2F")
//            .userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Mobile Safari/537.36")
//            .method(Connection.Method.GET)
//            .execute()
//        val cookie=res.cookies()
//        println(cookie.toString())
//        val res2=Jsoup.connect("https://www.acmicpc.net/signin")
//            .data("login_user_id","zaza0804","login_password","zaza4490^^"
//                ,"next","/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16%2C17%2C18%2C19%2C20%2C21&open=1&page=1",
//                "auto_login","on",
//                "g-recaptcha-response","03AFcWeA78GD_ZVT4B-g6ObNAcv6NaqwOpfk2QsWL24AFCYG_1aBYY6kxWJLX2crShLz2v41qPf2kEu-Ydt5DAUUxDpqYBpk6gT4JC6DIthoac8nxgRIqSHgvIMYbjjWZiQNN_1ByGWPBJV3V4Ayd6aoDn8CuccKEP8Oz3y_SN897vL2omWFDyoQ2x1_wPkRIS3ovNr8zuNBMbZRSsj9_0dPEoAqp_lMlnVvT2uyftIW1f7BeRXRETG_lidX5IcnmVjIyh5mlRVHow3vzWOH1AwykpqL0GhrN4YXRAORuNArNeQLi8CvVCKmyO_T52fkVMqSEdK8Kbd8IEL5-RLJM9szunBUIyWL1TAGh57YjdyDStj4yE_j2ZHmpKXfmWX4e-C3lVYat2_-w2Gx7CSNtlXpwT6szY8lJ4Mo1uhYgUY1QMXx0PcHMh6_KxK7vuM799djeQj8nkPSnyf0z2G-l4Ar1-OwSYSYpg--2brjyaswkRQxY_7TMj-E1J3oI50WjbeaMEbABhljpgIZp7yrqV1FrsUuI-523eEvoC7fphCZtp5vHMqAK-ZU7tDiQBbUxmuuePRQ3rekNbEyv0SuXvyFyqrjRJe6IFN02qC0qja9gk97LKUcwEttxT1BGD18NSzNGVbFvVTL234D6JShe7sjFY96VtNw8BouRMnoM2NT3WG7Jq5lk8SZY")
//            .header("Content-Type","application/x-www-form-urlencoded")
//            .userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Mobile Safari/537.36")
//            .header("Cookie", res.cookies().toString().substring(1,res.cookies().toString().length-1))
//            .method(Connection.Method.POST)
//            .execute()
//
//        println(res2.body())
        val res3=Jsoup.connect("https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16%2C17%2C18%2C19%2C20%2C21&page=1")
            .header("Cookie","OnlineJudge=7cf6ss89hvd7l1fpimlidf3chf; bojautologin=b6c19b09921d2020a32be9c7d78b281a5dfa26dd")
            .get()
            .getElementsByTag("tbody")
//        println(res3.getElementsByTag("tbody"))
//        val document: Document = Jsoup.parse(documents.html())
        val links = mutableListOf<BaekjoonProblem>()
        res3.select("tr").forEach{row->
            val tierSrc=row.select("td img").attr("src")
            val tier=tierSrc.substring(tierSrc.lastIndexOf('/')+1,tierSrc.lastIndexOf('.')).toInt()
            val href = row.select("td a").first()!!.attr("href")
            val title = row.select("td a").first()!!.text()
            val tries=row.select("td a").last()!!.text().toInt()

            links.add(
                BaekjoonProblem(
                problemLink = href,
                problemName = title,
                problemTier = tier,
                problemTry = tries
            )
            )
        }
        baekjoonProblemRepository.saveAll(links)
        links.forEach{
            println(it.toString())
        }
//        document.select("tr").forEach { row ->
//            val anchor = row.select("td a").first()
//            if (anchor != null) {
//                val link = anchor.attr("href")
//                val title = anchor.text()
//                links.add(Pair(link, title))
//            }
//        }
    }

    private fun splitTbody(tbody: Elements){
        val html=tbody.html()

    }
}