package com.th.plu.domain.domain.answer.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.answer.QAnswer.answer
import com.th.plu.domain.domain.answer.dto.EveryAnswerRetrievePageResponse
import com.th.plu.domain.domain.answer.dto.QEveryAnswerRetrievePageResponse
import com.th.plu.domain.domain.like.QLike.like
import org.springframework.stereotype.Repository


@Repository
class AnswerRepositoryImpl(private val queryFactory: JPAQueryFactory) : AnswerRepositoryCustom {
    override fun findAnswerById(id: Long): Answer? {
        return queryFactory
                .selectFrom(answer)
                .where(answer.id.eq(id))
                .fetchOne()
    }

    override fun findEveryAnswersWithCursorAndPageSize(questionId: Long, lastAnswerId: Long, pageSize: Long): List<EveryAnswerRetrievePageResponse> {
        return queryFactory
                .select(QEveryAnswerRetrievePageResponse(answer.id, like.answer.id.count(), answer.content))
                .from(answer)
                .leftJoin(like).on(like.answer.id.eq(answer.id))
                .where(
                        answer.isPublic.eq(true),
                        answer.question.id.eq(questionId),
                        answer.id.lt(lastAnswerId),
                )
                .groupBy(answer.id)
                .orderBy(answer.id.desc())
                .limit(pageSize)
                .fetch()
    }

    override fun findPublicAnswerCountByQuestionId(questionId: Long): Long {
        return queryFactory
                .select(answer.id.count())
                .from(answer)
                .where(answer.question.id.eq(questionId))
                .fetchOne()!!
    }
}
