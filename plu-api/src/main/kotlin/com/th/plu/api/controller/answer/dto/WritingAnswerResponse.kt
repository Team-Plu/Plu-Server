package com.th.plu.api.controller.answer.dto

import com.th.plu.api.controller.question.dto.QuestionResponse
import com.th.plu.domain.domain.answer.WritingAnswerResult

data class WritingAnswerResponse(
    val question: QuestionResponse,
    val answer: AnswerResponse,
    val reaction: ReactionResponse,
)

internal fun toWritingAnswerResponse(writingAnswerResult: WritingAnswerResult) = WritingAnswerResponse(
    question = QuestionResponse(
        id = writingAnswerResult.questionId,
        title = writingAnswerResult.questionTitle,
        content = writingAnswerResult.questionContent,
        exposedAt = writingAnswerResult.questionExposedAt,
        elementType = writingAnswerResult.questionElementType,
        characterImageUrl = writingAnswerResult.characterImageUrl,
    ),
    answer = AnswerResponse(
        id = writingAnswerResult.answerId,
        body = writingAnswerResult.answerBody,
    ),
    reaction = ReactionResponse(
        likeCount = writingAnswerResult.reactionLikeCount,
    )
)