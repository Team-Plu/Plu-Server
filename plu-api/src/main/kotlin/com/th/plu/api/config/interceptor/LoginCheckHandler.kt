package com.th.plu.api.config.interceptor

import com.th.plu.api.service.auth.jwt.JwtHandler
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.UnauthorizedException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class LoginCheckHandler(
    private val jwtHandler: JwtHandler
) {
    fun getMemberId(request: HttpServletRequest): Long {
        val bearerToken: String? = request.getHeader("Authorization")
        if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            val accessToken = bearerToken.substring("Bearer ".length)
            if (jwtHandler.validateToken(accessToken)) {
                val memberId = jwtHandler.getMemberIdFromJwt(accessToken)
                if (memberId != null) {
                    return memberId
                }
            }
        }
        throw UnauthorizedException(ErrorCode.UNAUTHORIZED_EXCEPTION, "잘못된 JWT $bearerToken 입니다.")
    }
}
