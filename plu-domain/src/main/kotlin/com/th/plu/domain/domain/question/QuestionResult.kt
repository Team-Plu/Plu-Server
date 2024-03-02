package com.th.plu.domain.domain.question

import java.time.LocalDateTime

data class QuestionResult(
    val questionId: Long,
    val title: String,
    val content: String,
    val elementType: ElementType,
    val exposedAt: LocalDateTime,
)
