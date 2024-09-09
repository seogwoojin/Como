package kote.demo.common.exception.company

import kote.demo.common.exception.ErrorCode
import kote.demo.common.exception.baseexception.EntityNotFoundException

class CompanyNotFoundException : EntityNotFoundException(ErrorCode.COMPANY_NOT_FOUND) {}
