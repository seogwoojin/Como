package kote.demo.common.exception

import kote.demo.common.exception.baseexception.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class GlobalExceptionHandler {
  private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

  @ExceptionHandler(BusinessException::class)
  protected fun handleBusinessException(exception: BusinessException, model: Model): ModelAndView {
    logger.error(exception.errorCode.code, exception)
    model.addAttribute("error", exception.message)
    return ModelAndView("error404", model.asMap(), HttpStatus.NOT_FOUND)
  }
  @ExceptionHandler(RuntimeException::class)
  fun handleRuntimeException(exception: RuntimeException, model: Model): ModelAndView {
    logger.error(exception.message, exception)

    // 에러 페이지와 상태 코드 설정
    model.addAttribute("error", exception.message)
    return ModelAndView("error500", model.asMap(), HttpStatus.INTERNAL_SERVER_ERROR)
  }
}
