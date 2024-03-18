package com.th.plu.domain.domain.like.explorer

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.NotFoundException
import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.like.Like
import com.th.plu.domain.domain.like.repository.LikeRepository
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.question.Question
import org.springframework.stereotype.Component

@Component
class LikeExplorer(
        private val likeRepository: LikeRepository
) {
    fun findLikeByMemberAndAnswerAndQuestion(member: Member, answer: Answer, question: Question): Like {
        return likeRepository.findLikeByMemberAndAnswerAndQuestion(member = member, answer = answer, question = question)
                ?: throw NotFoundException(ErrorCode.NOT_FOUND_LIKE_EXCEPTION, "존재하지 않는 공감 입니다")
    }
}