package com.th.plu.domain.domain.answer.repository

import com.th.plu.domain.domain.answer.Answer

interface AnswerRepositoryCustom {
    fun findAnswerById(id: Long): Answer?

    fun existsByMemberIdAndQuestionId(memberId: Long, questionId: Long): Boolean
}
