package com.th.plu.api.controller.auth.dto.request

import com.th.plu.api.controller.member.dto.request.CreateUserRequestDto
import com.th.plu.domain.domain.member.MemberSocialType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class SignupRequestDto(

    @field:Schema(description = "소셜 로그인 타입", example = "KAKAO")
    @field:NotNull(message = "socialType 을 입력해주세요.")
    val socialType: MemberSocialType,

    @field:Schema(description = "소셜 토큰", example = "eyJhbGciOiJIUzUxdfadfadsMiJ9.udnKnDSK08EuX56E5k-")
    @field:NotBlank(message = "token 을 입력해주세요.")
    val token: String,

    @field:Schema(description = "FCM 토큰", example = "adfaffaffdfsfewvasdvasvdsvffsddauaiviajvasvavisavja")
    @field:NotBlank(message = "fcmToken 을 입력해주세요.")
    val fcmToken: String,

    @field:Schema(description = "닉네임", example = "둘리")
    @field:NotBlank(message = "nickname 을 입력해주세요.")
    @field:Size(max = 8, message = "nickname 은 8자 이내로 입력해주세요.")
    val nickname: String
) {
    fun toCreateUserDto(socialId: String): CreateUserRequestDto {
        return CreateUserRequestDto(
            socialId = socialId,
            socialType = socialType,
            fcmToken = fcmToken,
            nickname = nickname
        )
    }
}
