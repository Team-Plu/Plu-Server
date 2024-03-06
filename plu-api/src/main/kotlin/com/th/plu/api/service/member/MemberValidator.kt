package com.th.plu.api.service.member

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.ConflictException
import com.th.plu.common.exception.model.IllegalArgumentException
import com.th.plu.common.exception.model.NotFoundException
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.member.MemberSocialType
import com.th.plu.domain.domain.member.Onboarding
import com.th.plu.domain.domain.member.repository.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberValidator(
    private val memberRepository: MemberRepository
) {
    fun validateNotExistsMember(socialId: String, socialType: MemberSocialType) {
        if (memberRepository.existBySocialIdAndSocialType(socialId, socialType)) {
            throw ConflictException(ErrorCode.CONFLICT_MEMBER_EXCEPTION, "이미 존재하는 유저 $socialId - $socialType 입니다")
        }
    }

    fun validateDuplicatedNickname(nickname: String) {
        if (memberRepository.existsByNickname(nickname)) {
            throw ConflictException(ErrorCode.CONFLICT_NICKNAME_EXCEPTION, "이미 사용 중인 닉네임입니다.")
        }
    }

    fun validateNullorBlankNickname(nickname: String?) {
        if (nickname.isNullOrBlank()) {
            throw IllegalArgumentException(ErrorCode.Illegal_ARGUMENT_NICKNAME_EXCEPTION, "닉네임은 비어 있을 수 없습니다.")
        }
    }

    fun validateOnboardingExists(member: Member): Onboarding {
        return member.onboarding
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_ONBOARDING_EXCEPTION, "Onboarding 정보가 없는 유저 입니다")
    }

}