package com.th.plu.domain.question.repository

import com.th.plu.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long>, QuestionRepositoryCustom {
}
