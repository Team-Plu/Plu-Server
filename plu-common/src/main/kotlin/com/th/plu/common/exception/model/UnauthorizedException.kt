package com.th.plu.common.exception.model

import com.th.plu.common.exception.code.ErrorCode

class UnauthorizedException(errorCode: ErrorCode, message: String) : PluException(errorCode, message)
