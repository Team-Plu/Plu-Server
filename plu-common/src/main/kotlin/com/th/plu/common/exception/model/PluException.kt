package com.th.plu.common.exception.model

import com.th.plu.common.exception.code.ErrorCode
import java.lang.RuntimeException

open class PluException(val errorCode: ErrorCode, message: String): RuntimeException(message) {
}
