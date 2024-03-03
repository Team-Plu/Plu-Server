package com.th.plu.api.controller.answer

import com.th.plu.api.config.interceptor.Auth
import com.th.plu.api.controller.answer.dto.response.AnswerInfoResponse
import com.th.plu.api.service.answer.AnswerService
import com.th.plu.common.dto.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Answer")
@RestController
@RequestMapping("/api")
class AnswerController(
        private val answerService: AnswerService
) {
    @Auth
    @Operation(summary = "답변 조회")
    @GetMapping("/v1/answer/{answerId}")
    fun findAnswerById(@PathVariable answerId: Long): ApiResponse<AnswerInfoResponse> {
        return ApiResponse.success(answerService.findAnswerInfoById(answerId))
    }

    @Operation(summary = "답변 좋아요")
    @PostMapping("/v1/answer/like/{answerId}")
    fun likeAnswer(@PathVariable answerId: Long): ApiResponse<Any> {
        answerService.likeAnswerById(1L, answerId)
        return ApiResponse.success()
    }
}