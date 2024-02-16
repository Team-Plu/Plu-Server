package com.th.plu.api.config.interceptor

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.UnauthorizedException
import com.th.plu.common.util.JwtUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class LoginCheckHandler(
    private val jwtUtils: JwtUtils
) {
    fun getMemberId(request: HttpServletRequest): Long {
        val bearerToken: String? = request.getHeader("Authorization")
        if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            val accessToken = bearerToken.substring("Bearer ".length)
            if (jwtUtils.validateToken(accessToken)) {
                val memberId = jwtUtils.getMemberIdFromJwt(accessToken)
                if (memberId != null) {
                    return memberId
                }
            }
        }
        throw UnauthorizedException(ErrorCode.UNAUTHORIZED_EXCEPTION, "잘못된 JWT $bearerToken 입니다.")
    }
}
