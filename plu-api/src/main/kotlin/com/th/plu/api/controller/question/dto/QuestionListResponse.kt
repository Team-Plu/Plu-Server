package com.th.plu.api.controller.question.dto

import com.th.plu.domain.domain.question.QuestionResult

data class QuestionListResponse(
    val answerCount: Int,
    val questions: List<QuestionResponse>,
)

internal fun toQuestionListResponse(resultList: List<QuestionResult>): QuestionListResponse = QuestionListResponse(
    answerCount = resultList.size,
    questions = resultList.map { toQuestionResponse(it) }
)