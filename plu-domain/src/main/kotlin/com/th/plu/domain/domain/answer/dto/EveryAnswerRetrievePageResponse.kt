package com.th.plu.domain.domain.answer.dto

import com.querydsl.core.annotations.QueryProjection

data class EveryAnswerRetrievePageResponse @QueryProjection constructor(
        val answerId: Long,
        val likeCount: Long,
        val answerContent: String
)