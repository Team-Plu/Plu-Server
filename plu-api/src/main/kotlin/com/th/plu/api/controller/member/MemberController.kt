package com.th.plu.api.controller.member

import com.th.plu.api.controller.member.dto.request.CheckNicknameRequestDto
import com.th.plu.api.controller.member.dto.response.CheckNicknameResponse
import com.th.plu.api.service.member.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import com.th.plu.common.dto.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Member")
@RestController
class MemberController(
        private val memberService: MemberService,
) {

    @Operation(summary = "닉네임 중복 체크")
    @PostMapping("/api/v1/member/nickname/dupl")
    fun checkNicknameDupl(@RequestBody request: CheckNicknameRequestDto): ApiResponse<CheckNicknameResponse> {
        val isAvailable = memberService.isNicknameAvailable(request.nickname)
        val response = CheckNicknameResponse(isAvailable)
        return ApiResponse.success(response)
    }
}