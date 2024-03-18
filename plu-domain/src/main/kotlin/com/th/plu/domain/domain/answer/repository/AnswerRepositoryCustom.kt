package com.th.plu.domain.domain.answer.repository

import com.th.plu.domain.domain.answer.Answer
import com.th.plu.domain.domain.answer.dto.EveryAnswerRetrievePageResponse

interface AnswerRepositoryCustom {
    fun findAnswerById(id: Long): Answer?

    fun findEveryAnswersWithCursorAndPageSize(questionId: Long, lastAnswerId: Long, pageSize: Long): List<EveryAnswerRetrievePageResponse>
}
