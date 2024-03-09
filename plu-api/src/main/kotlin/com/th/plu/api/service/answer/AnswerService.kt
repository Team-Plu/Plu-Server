package com.th.plu.api.service.answer

import com.th.plu.api.controller.answer.dto.response.AnswerInfoResponse
import com.th.plu.domain.domain.answer.explorer.AnswerExplorer
import com.th.plu.domain.domain.answer.explorer.QuestionExplorer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnswerService(
        private val questionExplorer: QuestionExplorer,
        private val answerExplorer: AnswerExplorer,
        private val answerValidator: AnswerValidator
) {
    @Transactional(readOnly = true)
    fun findAnswerInfoById(answerId: Long, memberId: Long): AnswerInfoResponse {
        val answer = answerExplorer.findAnswerById(answerId)
        if (!answer.isPublic) {
            answerValidator.validateIsMemberOwnerOfAnswer(answerId, memberId)
        }
        val question = questionExplorer.findQuestionById(answer.getQuestionId())

        return AnswerInfoResponse.of(question, answer)
    }
}