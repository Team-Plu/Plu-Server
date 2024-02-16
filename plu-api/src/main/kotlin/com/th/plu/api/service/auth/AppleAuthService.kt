package com.th.plu.api.service.auth

import com.th.plu.api.controller.auth.dto.request.LoginRequestDto
import com.th.plu.api.controller.auth.dto.request.SignupRequestDto
import com.th.plu.api.service.member.MemberService
import com.th.plu.api.service.member.MemberServiceUtils
import com.th.plu.domain.domain.member.MemberSocialType
import com.th.plu.domain.domain.member.repository.MemberRepository
import com.th.plu.external.client.apple.AppleTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AppleAuthService(
    private val appleTokenProvider: AppleTokenProvider,
    private val memberService: MemberService,
    private val memberRepository: MemberRepository
) : AuthService {

    companion object {
        private val socialType: MemberSocialType = MemberSocialType.APPLE
    }

    @Transactional
    override fun signup(request: SignupRequestDto): Long {
        val socialId = appleTokenProvider.getSocialIdFromIdToken(request.token)
        return memberService.registerUser(request.toCreateUserDto(socialId))
    }

    @Transactional
    override fun login(request: LoginRequestDto): Long {
        val socialId = appleTokenProvider.getSocialIdFromIdToken(request.token)
        val member = MemberServiceUtils.findMemberBySocialIdAndSocialType(memberRepository, socialId, socialType)
        member.updateFcmToken(request.fcmToken)
        return member.id!!
    }

}
