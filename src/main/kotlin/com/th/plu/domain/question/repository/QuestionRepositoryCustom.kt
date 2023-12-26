package com.th.plu.domain.question.repository

import com.th.plu.domain.question.Question


interface QuestionRepositoryCustom {
    fun findQuestionById(id: Long) : Question?
}
