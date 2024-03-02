package com.th.plu.api.controller.question.dto

import java.time.YearMonth

data class QuestionAnsweredResponse(
    val year: List<Int>,
    val yearMonth: List<YearMonthResponse>
)

data class YearMonthResponse(
    val year: Int,
    val month: Int,
)

internal fun toQuestionAnsweredResponse(yearMonths: Set<YearMonth>) = QuestionAnsweredResponse(
    year = yearMonths.map { it.year }.sortedDescending(),
    yearMonth = yearMonths.map { toYearMonthResponse(it) }
)

internal fun toYearMonthResponse(yearMonth: YearMonth) = YearMonthResponse(year = yearMonth.year, month = yearMonth.monthValue)