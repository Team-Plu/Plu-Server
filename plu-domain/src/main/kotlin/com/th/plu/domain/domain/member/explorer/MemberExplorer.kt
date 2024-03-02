package com.th.plu.domain.domain.member.explorer

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.NotFoundException
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.member.MemberSocialType
import com.th.plu.domain.domain.member.repository.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberExplorer(
    private val memberRepository: MemberRepository
) {
    fun findMemberById(id: Long): Member {
        return memberRepository.findMemberById(id)
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION, "존재하지 않는 유저 $id 입니다")
    }

    fun findMemberBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Member {
        return memberRepository.findMemberBySocialIdAndSocialType(socialId, socialType)
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION, "존재하지 않는 유저 $socialId $socialType 입니다")
    }
}