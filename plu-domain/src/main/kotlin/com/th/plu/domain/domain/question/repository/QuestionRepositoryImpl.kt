package com.th.plu.domain.domain.question.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.answer.QAnswer.answer
import com.th.plu.domain.domain.member.QMember.member
import com.th.plu.domain.domain.question.QQuestion.question
import com.th.plu.domain.domain.question.Question
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.YearMonth

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

    override fun findAllByExposedMonthIn(memberId: Long, yearMonth: YearMonth): List<Question> {
        return queryFactory
            .selectFrom(question)
            .innerJoin(member).on(member.id.eq(memberId))
            .innerJoin(answer).on(
                answer.member.id.eq(member.id),
                answer.question._id.eq(question._id),
            )
            .where(
                question.exposedAt.between(
                    // start (first day of month)
                    LocalDateTime.of(yearMonth.year, yearMonth.monthValue, 1, 0, 0),
                    // end (end day of month, 윤년 포함)
                    LocalDateTime.of(yearMonth.year, yearMonth.monthValue, yearMonth.month.maxLength(), 0, 0),
                )
            )
            .fetch()
    }
}
