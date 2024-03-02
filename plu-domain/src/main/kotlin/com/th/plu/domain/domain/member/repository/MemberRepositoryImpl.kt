package com.th.plu.domain.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.member.MemberSocialType
import com.th.plu.domain.domain.member.QMember.member
import com.th.plu.domain.domain.member.QOnboarding.onboarding
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
                        member.socialId.eq(socialId),
                        member.socialType.eq(socialType)
                ).fetchOne()
    }

    override fun existBySocialIdAndSocialType(socialId: String, socialType: MemberSocialType): Boolean {
        return queryFactory
                .selectFrom(member)
                .where(
                        member.socialId.eq(socialId),
                        member.socialType.eq(socialType)
                ).fetchOne() != null
    }

    override fun existsByNickname(nickname: String): Boolean {
        return queryFactory
                .selectFrom(onboarding)
                .where(onboarding.nickname.eq(nickname))
                .fetchOne() != null
    }
}
