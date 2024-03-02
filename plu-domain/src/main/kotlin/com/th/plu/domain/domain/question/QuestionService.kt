package com.th.plu.domain.domain.question

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.YearMonth

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
                elementType = todayQuestion.elementType,
                exposedAt = todayQuestion.exposedAt
            )
        }
    }

    @Transactional(readOnly = true)
    fun getQuestionsAnsweredMonthly(memberId: Long, selectedYearMonth: YearMonth): List<QuestionResult> =
        questionRetriever.findMyQuestionsMonthly(memberId, selectedYearMonth)
            .map {
                QuestionResult(
                    questionId = it.id,
                    title = it.title,
                    content = it.content,
                    elementType = it.elementType,
                    exposedAt = it.exposedAt
                )
            }
            .sortedByDescending { it.exposedAt } // 최신순 조회

    @Transactional(readOnly = true)
    fun getYearMonthAnswered(memberId: Long) : Set<YearMonth> =
        questionRetriever.findAnsweredYearMonth(memberId)
}