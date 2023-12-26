package com.th.plu.domain.answer.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.answer.Answer
import com.th.plu.domain.answer.QAnswer.answer

class AnswerRepositoryImpl(private val queryFactory: JPAQueryFactory): AnswerRepositoryCustom {
    override fun findAnswerById(id: Long): Answer? {
        return queryFactory
            .selectFrom(answer)
            .where(answer.id.eq(id))
            .fetchOne()
    }
}
