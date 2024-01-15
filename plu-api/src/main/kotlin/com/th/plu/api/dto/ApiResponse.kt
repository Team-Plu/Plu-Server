package com.th.plu.api.dto

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.code.SuccessCode

class ApiResponse<T>(val code: String, val message: String, var data: T?) {

    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(SuccessCode.OK.code, SuccessCode.OK.message, data)
        }

        fun success(): ApiResponse<Any> {
            return ApiResponse(SuccessCode.OK.code, SuccessCode.OK.message, null)
        }

        fun error(errorCode: ErrorCode): ApiResponse<Any> {
            return ApiResponse(errorCode.code, errorCode.message, null)
        }
    }
}
