package com.th.plu.domain.domain.answer.repository

import com.querydsl.core.Tuple
import com.th.plu.domain.domain.answer.Answer

interface AnswerRepositoryCustom {
    fun findAnswerById(id: Long): Answer?

    fun findTodayAnswersWithCursorAndPageSize(lastAnswerId: Long, pageSize: Long): List<Tuple>
}
