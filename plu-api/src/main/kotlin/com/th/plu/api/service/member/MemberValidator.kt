package com.th.plu.api.service.member

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.ConflictException
import com.th.plu.domain.domain.member.MemberSocialType
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
}