package com.th.plu.api.service.auth

import com.th.plu.domain.domain.member.MemberSocialType
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class AuthServiceProvider(
    private val appleAuthService: AppleAuthService,
    private val kakaoAuthService: KakaoAuthService
) {
    companion object {
        val authServiceMap = mutableMapOf<MemberSocialType, AuthService>()
    }

    @PostConstruct
    fun initAuthServiceMap() {
        authServiceMap[MemberSocialType.KAKAO] = kakaoAuthService
        authServiceMap[MemberSocialType.APPLE] = appleAuthService
    }

    fun getAuthService(socialType: MemberSocialType): AuthService {
        return authServiceMap[socialType]!!
    }
}
