package kote.demo.app.baekjoon.api;

import kote.demo.app.baekjoon.service.CodingTestService;
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
    @ResponseBody
    fun getProblemList(
        @RequestParam onlineJudge: String,
        @RequestParam bojAutoLogin: String,
        @RequestParam number:Int,
    ): String {
        if (onlineJudge.isBlank() || bojAutoLogin.isBlank()) throw Exception("Not Access")
        codingTestService.getInfoFromBaekjoon(onlineJudge, bojAutoLogin, number)
        return "OK"
    }

}
