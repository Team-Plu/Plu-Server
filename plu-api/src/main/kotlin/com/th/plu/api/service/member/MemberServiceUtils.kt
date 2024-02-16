package com.th.plu.api.service.member

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.ConflictException
import com.th.plu.common.exception.model.NotFoundException
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.member.MemberSocialType
import com.th.plu.domain.domain.member.repository.MemberRepository

class MemberServiceUtils {
    companion object {
        fun validateNotExistsMember(
            memberRepository: MemberRepository,
            socialId: String,
            socialType: MemberSocialType
        ) {
            if (memberRepository.existBySocialIdAndSocialType(socialId, socialType)) {
                throw ConflictException(ErrorCode.CONFLICT_MEMBER_EXCEPTION, "이미 존재하는 유저 $socialId - $socialType 입니다")
            }
        }

        fun findMemberBySocialIdAndSocialType(
            memberRepository: MemberRepository,
            socialId: String,
            socialType: MemberSocialType
        ): Member {
            return memberRepository.findMemberBySocialIdAndSocialType(socialId, socialType)
                ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION, "존재하지 않는 유저 $socialId $socialType 입니다")
        }
    }
}