package com.th.plu.api.controller.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class TokenRequestDto(

    @field:Schema(description = "토큰 - accessToken", example = "eyJhbGciOiJIUzUxMiJ9.udnKnDSK08EuX56E5k-")
    @field:NotBlank(message = "accessToken 을 입력해주세요.")
    val accessToken: String,

    @field:Schema(description = "토큰 - refreshToken", example = "eyJhbGciOiJIUzUxMiJ9.udnKnDSK08EuX56E5k-")
    @field:NotBlank(message = "refreshToken 을 입력해주세요.")
    val refreshToken: String
)