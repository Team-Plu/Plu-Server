package com.th.plu.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SettingRepositoryImpl(private val queryFactory: JPAQueryFactory): SettingRepositoryCustom {
}
