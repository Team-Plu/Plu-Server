package com.th.plu.api.controller.question.dto

import com.th.plu.domain.domain.question.QuestionResult

data class QuestionTodayResponse(
    val question: QuestionResponse,
)

internal fun toQuestionTodayResponse(result: QuestionResult): QuestionTodayResponse = QuestionTodayResponse(
    question = toQuestionResponse(result)
)