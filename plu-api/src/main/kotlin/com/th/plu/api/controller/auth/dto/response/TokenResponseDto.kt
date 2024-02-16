package com.th.plu.api.controller.auth.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class TokenResponseDto(

    @field:Schema(description = "PLU JWT accessToken")
    val accessToken: String,

    @field:Schema(description = "PLU JWT refreshToken")
    val refreshToken: String
)
