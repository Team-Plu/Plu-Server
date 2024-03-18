package com.th.plu.domain.domain.like.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.like.Like
import com.th.plu.domain.domain.like.QLike.like
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.question.Question
import org.springframework.stereotype.Repository

@Repository
class LikeRepositoryImpl(private val queryFactory: JPAQueryFactory) : LikeRepositoryCustom {
    override fun findLikeById(id: Long): Like? {
        return queryFactory
                .selectFrom(like)
                .where(like.id.eq(id))
                .fetchOne()
    }

    override fun existByMemberAndAnswerAndQuestion(member: Member, answer: Answer, question: Question): Boolean {
        return queryFactory
                .selectFrom(like)
                .where(
                        like.member.eq(member),
                        like.answer.eq(answer),
                        like.question.eq(question)
                ).fetchOne() != null
    }

    override fun findLikeByMemberAndAnswerAndQuestion(member: Member, answer: Answer, question: Question): Like? {
        return queryFactory
                .selectFrom(like)
                .where(
                        like.member.eq(member),
                        like.answer.eq(answer),
                        like.question.eq(question)
                ).fetchOne()
    }
}
