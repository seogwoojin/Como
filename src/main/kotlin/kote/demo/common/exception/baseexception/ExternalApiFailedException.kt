package kote.demo.common.exception.baseexception

import kote.demo.common.exception.ErrorCode

open class ExternalApiFailedException : BusinessException {

  constructor(errorCode: ErrorCode) : super(errorCode)

  constructor(message: String, errorCode: ErrorCode) : super(message, errorCode)
}
