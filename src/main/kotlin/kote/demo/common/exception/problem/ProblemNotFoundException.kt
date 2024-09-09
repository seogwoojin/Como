package kote.demo.common.exception.problem

import kote.demo.common.exception.ErrorCode
import kote.demo.common.exception.baseexception.EntityNotFoundException

class ProblemNotFoundException : EntityNotFoundException(ErrorCode.PROBLEM_NOT_FOUND)
