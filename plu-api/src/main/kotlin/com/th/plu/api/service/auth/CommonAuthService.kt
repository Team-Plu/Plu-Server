package com.th.plu.api.service.auth

import com.th.plu.api.service.auth.jwt.JwtHandler
import com.th.plu.domain.domain.member.explorer.MemberExplorer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommonAuthService(
    private val memberExplorer: MemberExplorer,
    private val jwtHandler: JwtHandler
) {

    @Transactional
    fun logout(memberId: Long) {
        val member = memberExplorer.findMemberById(memberId)
        jwtHandler.expireRefreshToken(member.id!!)
        member.resetFcmToken()
    }
}