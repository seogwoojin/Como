package kote.demo.common.exception.problem

import kote.demo.common.exception.ErrorCode
import kote.demo.common.exception.baseexception.BusinessException

class AlgorithmChooseException : BusinessException(ErrorCode.ALGORITHM_CHOOSE_ERROR)