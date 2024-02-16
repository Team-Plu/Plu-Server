package com.th.plu.api.service.auth

import com.th.plu.api.controller.auth.dto.response.TokenResponseDto
import com.th.plu.common.util.JwtUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenService(
    private val jwtUtils: JwtUtils
) {
    @Transactional
    fun createTokenInfo(memberId: Long): TokenResponseDto {
        val tokens: List<String> = jwtUtils.createTokenInfo(memberId)
        return TokenResponseDto(accessToken = tokens[0], refreshToken = tokens[1])
    }
}
