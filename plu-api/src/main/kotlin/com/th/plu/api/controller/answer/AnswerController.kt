package com.th.plu.api.controller.answer

import com.th.plu.api.config.resolver.MemberId
import com.th.plu.api.controller.answer.dto.WritingAnswerRequestDto
import com.th.plu.api.controller.answer.dto.WritingAnswerResponseDto
import com.th.plu.api.controller.answer.dto.toAnswerWriting
import com.th.plu.api.controller.answer.dto.toWritingAnswerResponse
import com.th.plu.common.dto.response.ApiResponse
import com.th.plu.api.service.answer.AnswerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Answers")
@RestController
class AnswerController(
    private val answerService: AnswerService,
) {
    @Operation(summary = "오늘의 질문 답변하기")
    @PostMapping("/api/v1/answers/{questionId}")
    fun writeAnswer(
        @MemberId memberId: Long,
        @PathVariable("questionId") questionId: Long,
        @RequestBody writingAnswerRequestDto: WritingAnswerRequestDto,
    ): ApiResponse<WritingAnswerResponseDto> =
        answerService.writeAnswer(memberId, questionId, toAnswerWriting(writingAnswerRequestDto)).let {
            ApiResponse.success(toWritingAnswerResponse(it))
        }
}