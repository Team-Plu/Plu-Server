package com.th.plu.common.exception.model

import com.th.plu.common.exception.code.ErrorCode

class BadGatewayException(errorCode: ErrorCode, message: String): PluException(errorCode, message) {
}
