package com.th.plu.api.controller.question.dto

import com.th.plu.domain.domain.question.ElementType
import com.th.plu.domain.domain.question.QuestionResult
import java.time.LocalDateTime

data class QuestionResponse(
    val title: String,
    val content: String,
    val exposedAt: LocalDateTime,
    val characterImageUrl: String,
    val elementType: ElementType,
)

internal fun toQuestionResponse(result: QuestionResult) = QuestionResponse(
    title = result.title,
    content = result.content,
    exposedAt = result.exposedAt,
    characterImageUrl = result.elementType.characterImageUrl,
    elementType = result.elementType,
)