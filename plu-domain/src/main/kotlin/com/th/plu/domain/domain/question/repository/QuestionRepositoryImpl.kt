package com.th.plu.domain.domain.question.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.question.QQuestion.question
import com.th.plu.domain.domain.question.Question
import org.springframework.stereotype.Repository

@Repository
class QuestionRepositoryImpl(private val queryFactory: JPAQueryFactory) : QuestionRepositoryCustom {

    override fun findQuestionById(id: Long): Question? {
        return queryFactory
            .selectFrom(question)
            .where(question.id.eq(id))
            .fetchOne()
    }
}
