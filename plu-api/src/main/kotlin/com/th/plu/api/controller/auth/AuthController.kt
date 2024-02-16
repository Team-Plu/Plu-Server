package com.th.plu.api.controller.auth

import com.th.plu.api.controller.auth.dto.request.SignupRequestDto
import com.th.plu.api.controller.auth.dto.response.TokenResponseDto
import com.th.plu.api.service.auth.AuthServiceProvider
import com.th.plu.api.service.auth.TokenService
import com.th.plu.api.service.member.MemberService
import com.th.plu.common.dto.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth")
@RestController
@RequestMapping("/api")
class AuthController(
    private val authServiceProvider: AuthServiceProvider,
    private val memberService: MemberService,
    private val tokenService: TokenService
) {

    @Operation(summary = "카카오 소셜 회원가입")
    @PostMapping("/v1/auth/signup")
    fun signup(@Valid @RequestBody request: SignupRequestDto): ApiResponse<TokenResponseDto> {
        val authService = authServiceProvider.getAuthService(request.socialType)
        val memberId = authService.signup(request)
        val tokenInfo = tokenService.createTokenInfo(memberId)
        return ApiResponse.success(tokenInfo)
    }
}