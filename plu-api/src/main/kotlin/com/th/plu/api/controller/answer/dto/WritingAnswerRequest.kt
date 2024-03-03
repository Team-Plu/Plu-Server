package com.th.plu.api.controller.answer.dto

import com.th.plu.domain.domain.answer.AnswerWriting

data class WritingAnswerRequest(
    val body: String,
    val settings: AnswerSettingRequest,
)

data class AnswerSettingRequest(
    val open: Boolean,
)

fun toAnswerWriting(writingAnswerRequest: WritingAnswerRequest) = AnswerWriting(
    body = writingAnswerRequest.body,
    open = writingAnswerRequest.settings.open,
)