package kote.demo.baekjoon.api;

import kote.demo.baekjoon.service.CodingTestService;
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("baekjoon")
class BaekjoonScrapController(
        private val codingTestService:CodingTestService
) {
    @GetMapping("")
    fun getAdminPage():String
    {
        return "admin"
    }

    @PostMapping(path = [""], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getProblemList(
        @RequestParam onlineJudge: String?,
        @RequestParam bojAutoLogin: String?
    ): ResponseEntity.BodyBuilder {
        println(onlineJudge)
        println(bojAutoLogin)
        if (onlineJudge.isNullOrBlank() || bojAutoLogin.isNullOrBlank()) throw Exception("Not Access")
        codingTestService.getInfoFromBaekjoon(onlineJudge, bojAutoLogin)
        return ResponseEntity.status(HttpStatus.OK)
    }

}
