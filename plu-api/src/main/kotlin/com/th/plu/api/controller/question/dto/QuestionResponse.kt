package com.th.plu.api.controller.question.dto

import com.th.plu.domain.domain.question.QuestionResult

data class QuestionResponse(
    val title: String,
    val content: String,
    val characterImageUrl: String,
)

internal fun toQuestionResponse(result: QuestionResult) = QuestionResponse(
    title = result.title,
    content = result.content,
    characterImageUrl = result.characterImageUrl
)