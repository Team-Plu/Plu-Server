package com.th.plu.api.service.member

import com.th.plu.api.controller.member.dto.request.CreateUserRequestDto
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.member.Onboarding
import com.th.plu.domain.domain.member.Setting
import com.th.plu.domain.domain.member.repository.MemberRepository
import com.th.plu.domain.domain.member.repository.OnboardingRepository
import com.th.plu.domain.domain.member.repository.SettingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val onboardingRepository: OnboardingRepository,
    private val settingRepository: SettingRepository
) {

    @Transactional
    fun registerUser(request: CreateUserRequestDto): Long {
        MemberServiceUtils.validateNotExistsMember(memberRepository, request.socialId, request.socialType)
        // TODO: 닉네임 중복 체크 추가해야합니다.
        val member = memberRepository.save(
            Member.newInstance(
                socialId = request.socialId,
                socialType = request.socialType,
                fcmToken = request.fcmToken,
                setting = settingRepository.save(Setting.newInstance())
            )
        )
        val onboarding = onboardingRepository.save(
            Onboarding.newInstance(
                member = member,
                nickname = request.nickname
            )
        )
        member.initOnboarding(onboarding)
        return member.id!!
    }

}