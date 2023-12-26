package com.th.plu.domain.answer.repository

import com.th.plu.domain.answer.Answer

interface AnswerRepositoryCustom {
    fun findAnswerById(id: Long): Answer?
}
