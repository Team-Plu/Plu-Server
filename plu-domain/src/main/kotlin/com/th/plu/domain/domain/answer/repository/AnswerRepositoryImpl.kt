package com.th.plu.domain.domain.answer.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.answer.QAnswer.answer
import org.springframework.stereotype.Repository

@Repository
class AnswerRepositoryImpl(private val queryFactory: JPAQueryFactory) : AnswerRepositoryCustom {
    override fun findAnswerById(id: Long): Answer? {
        return queryFactory
            .selectFrom(answer)
            .where(answer._id.eq(id))
            .fetchOne()
    }
}
