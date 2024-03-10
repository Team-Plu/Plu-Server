package com.th.plu.domain.domain.member.repository

import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.member.MemberSocialType

interface MemberRepositoryCustom {

    fun findMemberById(id: Long): Member?
    fun findMemberBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Member?
    fun existBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Boolean
    fun existsByNickname(nickname: String): Boolean

}
