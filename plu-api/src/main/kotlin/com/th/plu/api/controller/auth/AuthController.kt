package com.th.plu.api.controller.auth

import com.th.plu.api.config.interceptor.Auth
import com.th.plu.api.config.resolver.MemberId
import com.th.plu.api.controller.auth.dto.request.LoginRequestDto
import com.th.plu.api.controller.auth.dto.request.SignupRequestDto
import com.th.plu.api.controller.auth.dto.request.TokenRequestDto
import com.th.plu.api.controller.auth.dto.response.TokenResponseDto
import com.th.plu.api.service.auth.AuthServiceProvider
import com.th.plu.api.service.auth.CommonAuthService
import com.th.plu.api.service.auth.jwt.TokenService
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
    private val tokenService: TokenService,
    private val commonAuthService: CommonAuthService
) {

    @Operation(summary = "소셜 회원가입")
    @PostMapping("/v1/auth/signup")
    fun signup(@Valid @RequestBody request: SignupRequestDto): ApiResponse<TokenResponseDto> {
        val authService = authServiceProvider.getAuthService(request.socialType)
        val memberId = authService.signup(request)
        val tokenInfo = tokenService.createTokenInfo(memberId)
        return ApiResponse.success(tokenInfo)
    }

    @Operation(summary = "소셜 로그인")
    @PostMapping("/v1/auth/login")
    fun login(@Valid @RequestBody request: LoginRequestDto): ApiResponse<TokenResponseDto> {
        val authService = authServiceProvider.getAuthService(request.socialType)
        val memberId = authService.login(request)
        val tokenInfo = tokenService.createTokenInfo(memberId)
        return ApiResponse.success(tokenInfo)
    }

    @Operation(summary = "토큰 갱신")
    @PostMapping("/v1/auth/refresh")
    fun reissue(@Valid @RequestBody request: TokenRequestDto): ApiResponse<TokenResponseDto> {
        return ApiResponse.success(tokenService.reissueToken(request))
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/v1/auth/logout")
    @Auth
    fun logout(@MemberId memberId: Long): ApiResponse<Any> {
        commonAuthService.logout(memberId)
        return ApiResponse.success()
    }
}