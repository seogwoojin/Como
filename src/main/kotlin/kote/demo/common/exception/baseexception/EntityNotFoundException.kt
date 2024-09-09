package kote.demo.common.exception.baseexception

import kote.demo.common.exception.ErrorCode

open class EntityNotFoundException : BusinessException {

  constructor(errorCode: ErrorCode) : super(errorCode)

  constructor(message: String) : super(message, ErrorCode.PROBLEM_NOT_FOUND)
}
