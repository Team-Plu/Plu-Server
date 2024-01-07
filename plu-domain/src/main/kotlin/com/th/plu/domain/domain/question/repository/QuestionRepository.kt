package com.th.plu.domain.domain.question.repository

import com.th.plu.domain.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long>, QuestionRepositoryCustom {
}
