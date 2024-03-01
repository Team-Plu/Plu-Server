package com.th.plu.api.service.auth.jwt

import com.th.plu.api.controller.auth.dto.response.TokenResponseDto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
) {
    fun toResponseDto(): TokenResponseDto {
        return TokenResponseDto(accessToken, refreshToken)
    }
}
