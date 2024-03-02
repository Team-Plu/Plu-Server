package com.th.plu.domain.domain.question

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.InternalServerException
import com.th.plu.domain.domain.question.repository.QuestionRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class QuestionRetriever(
    private val questionRepository: QuestionRepository,
) {
    fun findQuestion(date: LocalDateTime): Question =
        questionRepository.findByExposedAtOrNull(date) ?: throw InternalServerException(
            ErrorCode.DATA_NOT_READY_EXCEPTION,
            "($date) 날짜의 질문데이터가 준비되지 않았습니다. "
        )
}