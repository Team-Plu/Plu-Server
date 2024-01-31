package com.th.plu.common.exception.model

import com.th.plu.common.exception.code.ErrorCode

class IllegalArgumentException(errorCode: ErrorCode, message: String): PluException(errorCode, message) {
}
