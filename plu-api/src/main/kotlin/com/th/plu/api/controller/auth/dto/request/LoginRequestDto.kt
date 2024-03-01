package com.th.plu.api.controller.auth.dto.request

import com.th.plu.domain.domain.member.MemberSocialType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


data class LoginRequestDto(

    @field:Schema(description = "소셜 로그인 타입", example = "KAKAO")
    @field:NotNull(message = "socialType 을 입력해주세요.")
    val socialType: MemberSocialType,

    @field:Schema(description = "소셜 토큰", example = "eyJhbGciOiJIUzUxdfadfadsMiJ9.udnKnDSK08EuX56E5k-")
    @field:NotBlank(message = "token 을 입력해주세요.")
    val token: String,

    @field:Schema(description = "FCM 토큰", example = "adfaffaffdfsfewvasdvasvdsvffsddauaiviajvasvavisavja")
    @field:NotBlank(message = "fcmToken 을 입력해주세요.")
    val fcmToken: String
)
