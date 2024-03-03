package com.th.plu.domain.domain.like.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.like.Like
import com.th.plu.domain.domain.like.QLike.like
import org.springframework.stereotype.Repository

@Repository
class LikeRepositoryImpl(private val queryFactory: JPAQueryFactory) : LikeRepositoryCustom {
    override fun findLikeById(id: Long): Like? {
        return queryFactory
                .selectFrom(like)
                .where(like.id.eq(id))
                .fetchOne();
    }
}
