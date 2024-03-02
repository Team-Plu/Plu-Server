package com.th.plu.domain.domain.question

data class QuestionResult(
    val questionId: Long,
    val title: String,
    val content: String,
    val characterImageUrl: String,
)
