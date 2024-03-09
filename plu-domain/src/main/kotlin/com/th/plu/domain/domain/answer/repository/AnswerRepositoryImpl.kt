package com.th.plu.domain.domain.answer.repository

import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.answer.QAnswer.answer
import com.th.plu.domain.domain.like.QLike.like
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.LocalTime


@Repository
class AnswerRepositoryImpl(private val queryFactory: JPAQueryFactory) : AnswerRepositoryCustom {
    override fun findAnswerById(id: Long): Answer? {
        return queryFactory
                .selectFrom(answer)
                .where(answer.id.eq(id))
                .fetchOne()
    }

    override fun findTodayAnswersWithCursorAndPageSize(lastAnswerId: Long, pageSize: Long): List<Tuple> {
        val startOfDay = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.MAX)
        val endOfDay = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.MIN)

        return queryFactory
                .select(answer.id, like.answer.id.count(), answer.content)
                .from(answer)
                .leftJoin(like).on(like.answer.id.eq(answer.id))
                .where(
                        answer.isPublic.eq(true),
                        answer.id.lt(lastAnswerId),
                        answer.modifiedAt.gt(startOfDay),
                        answer.modifiedAt.lt(endOfDay)
                )
                .groupBy(answer.id)
                .orderBy(answer.id.desc())
                .limit(pageSize)
                .fetch()
    }
}
