package com.th.plu.domain.domain.answer.repository

import com.th.plu.domain.domain.answer.Answer
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository : JpaRepository<Answer, Long>, AnswerRepositoryCustom {
}
