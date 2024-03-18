package com.th.plu.api.service.like

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.ConflictException
import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.like.repository.LikeRepository
import com.th.plu.domain.domain.member.Member
import com.th.plu.domain.domain.question.Question
import org.springframework.stereotype.Component

@Component
class LikeValidator(
        private val likeRepository: LikeRepository
) {
    fun validateNotExistLike(member: Member, answer: Answer, question: Question) {
        if (likeRepository.existByMemberAndAnswerAndQuestion(member = member, answer = answer, question = question)) {
            throw ConflictException(ErrorCode.CONFLICT_LIKE_EXCEPTION, "이미 회원(ID: ${member.id}은 해당 답변(ID: ${answer.id})에 대한 공감이 되어있습니다.")
        }
    }
}