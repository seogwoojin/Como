package kote.demo.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: String, val message: String, var status: Int) {
    // Common
    INVALID_INPUT_VALUE("C01", "Invalid Input Value.", HttpStatus.BAD_REQUEST.value()),
    METHOD_NOT_ALLOWED("C02", "Invalid Method Type.", HttpStatus.METHOD_NOT_ALLOWED.value()),
    ENTITY_NOT_FOUND("C03", "Entity Not Found.", HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR(
        "C04",
        "Internal Server Error.",
        HttpStatus.INTERNAL_SERVER_ERROR.value()
    ),

    COMPANY_NOT_FOUND("C04", "Company Not Found.", HttpStatus.BAD_REQUEST.value()),
    PROBLEM_NOT_FOUND("P01", "Problem Not Found.", HttpStatus.BAD_REQUEST.value()),
    ALGORITHM_CHOOSE_ERROR("A01", "Cannot Choose Algorithm.", HttpStatus.INTERNAL_SERVER_ERROR.value())
}
