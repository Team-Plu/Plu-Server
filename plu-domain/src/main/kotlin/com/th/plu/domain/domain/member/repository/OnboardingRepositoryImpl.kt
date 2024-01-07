package com.th.plu.domain.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class OnboardingRepositoryImpl(private val queryFactory: JPAQueryFactory): OnboardingRepositoryCustom {
}
