package com.th.plu.domain.domain.question.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.question.QQuestion.question
import com.th.plu.domain.domain.question.Question
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class QuestionRepositoryImpl(private val queryFactory: JPAQueryFactory) : QuestionRepositoryCustom {

    override fun findQuestionById(id: Long): Question? {
        return queryFactory
            .selectFrom(question)
            .where(question._id.eq(id))
            .fetchOne()
    }

    override fun findByExposedAtOrNull(exposedAt: LocalDateTime): Question? {
        return queryFactory
            .selectFrom(question)
            .where(question.exposedAt.eq(exposedAt))
            .fetchOne()
    }
}
