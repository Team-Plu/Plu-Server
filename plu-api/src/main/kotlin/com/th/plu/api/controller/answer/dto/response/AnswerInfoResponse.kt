package com.th.plu.api.controller.answer.dto.response

import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.question.Question
import java.time.LocalDateTime

data class AnswerInfoResponse(
        val questionDate: LocalDateTime,
        val questionTitle: String,
        val answer: String,
        val likeCount: Int,
        val elementImageUrl: String,
        val colorCode: String
) {
    companion object {
        fun of(question: Question, answer: Answer): AnswerInfoResponse {
            return AnswerInfoResponse(
                    questionDate = question.modifiedAt,
                    questionTitle = question.title,
                    answer = answer.content,
                    likeCount = answer.getLikeCount(),
                    elementImageUrl = question.elementType.elementImageUrl,
                    colorCode = question.elementType.colorCode
            )
        }
    }
}