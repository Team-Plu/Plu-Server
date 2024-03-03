package com.th.plu.api.service.member

import com.th.plu.api.controller.member.dto.request.CreateUserRequestDto
import com.th.plu.api.controller.member.dto.response.MyPageResponse
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.NotFoundException
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.member.Onboarding
import com.th.plu.domain.domain.member.Setting
import com.th.plu.domain.domain.member.explorer.MemberExplorer
import com.th.plu.domain.domain.member.repository.MemberRepository
import com.th.plu.domain.domain.member.repository.OnboardingRepository
import com.th.plu.domain.domain.member.repository.SettingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
        private val memberValidator: MemberValidator,
        private val memberRepository: MemberRepository,
        private val onboardingRepository: OnboardingRepository,
        private val settingRepository: SettingRepository,
        private val memberExplorer: MemberExplorer
) {

    @Transactional
    fun registerUser(request: CreateUserRequestDto): Long {
        memberValidator.validateNotExistsMember(request.socialId, request.socialType)
        memberValidator.validateDuplicatedNickname(request.nickname)

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

    @Transactional(readOnly = true)
    fun isNicknameAvailable(nickname: String): Boolean {
        return !memberRepository.existsByNickname(nickname)
    }

    @Transactional
    fun updateNickname(memberId: Long, newNickname: String) {
        val member = memberExplorer.findMemberById(memberId)

        memberValidator.validateDuplicatedNickname(newNickname)

        //onboarding 객체가 null이라면 예외 발생
        member.onboarding?.let {
            it.nickname = newNickname
            onboardingRepository.save(it)
        } ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION, "Onboarding 정보가 없는 유저 $memberId 입니다")
    }

    @Transactional
    fun deleteMember(memberId: Long) {
        val member = memberExplorer.findMemberById(memberId)
        memberRepository.delete(member)
    }

    @Transactional(readOnly = true)
    fun getMyPageInfo(memberId: Long): MyPageResponse {
        val member = memberExplorer.findMemberById(memberId)
        return MyPageResponse(
                nickname = member.onboarding?.nickname ?: "",
                notificationStatus = member.setting.notificationStatus
        )
    }

}