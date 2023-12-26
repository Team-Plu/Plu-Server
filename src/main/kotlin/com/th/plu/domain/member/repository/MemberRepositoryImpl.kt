package com.th.plu.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.member.Member
import com.th.plu.domain.member.MemberSocialType
import com.th.plu.domain.member.QMember.member
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(private val queryFactory: JPAQueryFactory) : MemberRepositoryCustom {
    override fun findMemberById(id: Long): Member? {
        return queryFactory
            .selectFrom(member)
            .where(member.id.eq(id))
            .fetchOne()
    }

    override fun findMemberBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Member? {
        return queryFactory
            .selectFrom(member)
            .where(
                member.socialInfo.socialId.eq(socialId),
                member.socialInfo.socialType.eq(socialType)
            ).fetchOne()
    }

    override fun existBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Boolean {
        return queryFactory
            .selectFrom(member)
            .where(
                member.socialInfo.socialId.eq(socialId),
                member.socialInfo.socialType.eq(socialType)
            ).fetchOne() != null
    }
}
