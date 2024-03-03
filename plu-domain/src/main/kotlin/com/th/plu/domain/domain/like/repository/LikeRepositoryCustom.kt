package com.th.plu.domain.domain.like.repository

import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.like.Like
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.question.Question

interface LikeRepositoryCustom {

    fun findLikeById(id: Long): Like?

    fun existByMemberAndAnswerAndQuestion(member: Member, answer: Answer, question: Question): Boolean
}
