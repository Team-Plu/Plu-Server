package com.th.plu.domain.domain.question.repository

import com.th.plu.domain.domain.question.Question
import java.time.LocalDateTime


interface QuestionRepositoryCustom {
    fun findQuestionById(id: Long): Question?

    fun findByExposedAtOrNull(exposedAt: LocalDateTime): Question?
}
