package kote.demo.baekjoon.api

import jakarta.servlet.http.HttpServletRequest
import kote.demo.baekjoon.Customer
import kote.demo.baekjoon.CustomerRepository
import kote.demo.baekjoon.dto.TestResponseDto
import kote.demo.baekjoon.service.CodingTestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("")
class CodingTestController(
    private val customerRepository: CustomerRepository,
    private val codingTestService: CodingTestService
) {
    @GetMapping
    fun getMain(model: Model):String{
        customerRepository.save(
            Customer(
                name = "woojin"
            )
        )
        println(customerRepository.findAll()[0].name)
        model.addAttribute("data", customerRepository.findAll()[0].name)
        return "main"
    }

    @PostMapping
    fun getProblemList(
            request:HttpServletRequest
        ): ResponseEntity<TestResponseDto.Problem> {
        val url="https://www.acmicpc.net/problemset?sort=submit_desc&tier=5%2C6%2C7%2C8%2C9%2C10%2C11%2C12%2C13%2C14%2C15%2C16%2C17%2C18%2C19%2C20%2C21&open=1&page=1"
//        val document =codingTestService.fetchPage(url)
        println(codingTestService.parseHtml(url))
        return ResponseEntity.status(HttpStatus.CREATED).body(TestResponseDto.Problem(
            name="상어가족"
        ))
    }
}