package com.th.plu.api.service.answer

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.ValidationException
import com.th.plu.domain.domain.answer.explorer.AnswerExplorer
import com.th.plu.domain.domain.answer.repository.AnswerRepository
import org.springframework.stereotype.Component

@Component
class AnswerValidator(
        private val answerExplorer: AnswerExplorer,
        private val answerRepository: AnswerRepository
) {
    fun validateIsMemberOwnerOfAnswer(answerId: Long, memberId: Long) {
        val answer = answerExplorer.findAnswerById(answerId)
        if (answer.member.id != memberId) {
            throw ValidationException(ErrorCode.INVALID_ANSWER_OWNER,
                    "멤버 (ID: ${memberId})는 답변 (ID: ${answerId})의 답변자가 아니기 때문에 답변 정보에 접근할 수 없습니다.")
        }
    }
}