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
) {
    @Transactional(readOnly = true)
    fun findAnswerInfoById(id: Long): AnswerInfoResponse {
        val answer = answerExplorer.findAnswerById(id)
        val question = questionExplorer.findQuestionById(answer.getQuestionId())

        return AnswerInfoResponse.of(question, answer)
    }
}