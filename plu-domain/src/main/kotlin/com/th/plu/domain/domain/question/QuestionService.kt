package com.th.plu.domain.domain.question

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class QuestionService(
    private val questionRetriever: QuestionRetriever,
) {
    @Transactional(readOnly = true)
    fun getQuestionToday(): QuestionResult {
        val today = LocalDateTime.now()
        return questionRetriever.findQuestion(today).let { todayQuestion ->
            QuestionResult(
                questionId = todayQuestion.id,
                title = todayQuestion.title,
                content = todayQuestion.content,
                characterImageUrl = todayQuestion.elementType.characterImageUrl
            )
        }
    }
}