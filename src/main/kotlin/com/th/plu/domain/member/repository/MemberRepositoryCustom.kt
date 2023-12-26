package com.th.plu.domain.member.repository

import com.th.plu.domain.member.Member
import com.th.plu.domain.member.MemberSocialType

interface MemberRepositoryCustom {

    fun findMemberById(id: Long): Member?
    fun findMemberBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Member?
    fun existBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Boolean
}
