package kote.demo.baekjoon.dto

class TestResponseDto {
    data class Problem(
        var name: String?=null,
        var level: String?=null,
        var link: String?=null
    )
}