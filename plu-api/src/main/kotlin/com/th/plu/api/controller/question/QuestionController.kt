package com.th.plu.api.controller.question

import com.th.plu.api.controller.question.dto.QuestionTodayResponse
import com.th.plu.api.controller.question.dto.toQuestionTodayResponse
import com.th.plu.common.dto.response.ApiResponse
import com.th.plu.domain.domain.question.QuestionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Questions")
@RestController
class QuestionController(
    private val questionService: QuestionService,
) {
    @Operation(summary = "오늘의 질문")
    @GetMapping("/api/v1/questions/today")
    fun getQuestionToday(): ApiResponse<QuestionTodayResponse> = questionService.getQuestionToday().let {
        ApiResponse.success(toQuestionTodayResponse(it))
    }
}