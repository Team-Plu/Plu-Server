package com.th.plu.api.service.auth

import com.th.plu.api.service.member.MemberServiceUtils
import com.th.plu.common.util.JwtUtils
import com.th.plu.domain.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommonAuthService(
    private val memberRepository: MemberRepository,
    private val jwtUtils: JwtUtils
) {

    @Transactional
    fun logout(memberId: Long) {
        val member = MemberServiceUtils.findMemberById(memberRepository, memberId)
        jwtUtils.expireRefreshToken(member.id!!)
        member.resetFcmToken()
    }
}