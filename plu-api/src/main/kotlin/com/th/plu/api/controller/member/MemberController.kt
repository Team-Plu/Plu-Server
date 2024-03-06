package com.th.plu.api.controller.member

import com.th.plu.api.config.interceptor.Auth
import com.th.plu.api.config.resolver.MemberId
import com.th.plu.api.controller.member.dto.request.CheckNicknameRequestDto
import com.th.plu.api.controller.member.dto.request.UpdateNicknameRequestDto
import com.th.plu.api.controller.member.dto.response.CheckNicknameResponse
import com.th.plu.api.controller.member.dto.response.MyPageResponseDto
import com.th.plu.api.service.member.MemberService
import com.th.plu.common.dto.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Member")
@RestController
class MemberController(
    private val memberService: MemberService,
) {
    @Operation(summary = "닉네임 중복 체크")
    @PostMapping("/api/v1/member/nickname/dupl")
    fun checkNicknameDuplication(@RequestBody request: CheckNicknameRequestDto): ApiResponse<CheckNicknameResponse> {
        val response = memberService.checkNicknameDuplication(request)
        return ApiResponse.success(response)
    }

    @Auth
    @Operation(summary = "[인증] 닉네임 수정")
    @PutMapping("/api/v1/member/{memberId}/nickname")
    fun updateNickname(
        @PathVariable memberId: Long,
        @RequestBody request: UpdateNicknameRequestDto
    ): ApiResponse<Any> {
        memberService.updateNickname(memberId, request.newNickname)
        return ApiResponse.success()
    }

    @Auth
    @Operation(summary = "[인증] 회원 탈퇴")
    @DeleteMapping("/api/v1/member")
    fun deleteMember(@MemberId memberId: Long): ApiResponse<Any> {
        memberService.deleteMember(memberId)
        return ApiResponse.success()
    }

    @Auth
    @Operation(summary = "[인증] 마이페이지 조회")
    @GetMapping("/api/v1/mypage")
    fun getMyPageInfo(@MemberId memberId: Long): ApiResponse<MyPageResponseDto> {
        val myPageInfo = memberService.getMyPageInfo(memberId)
        return ApiResponse.success(data = myPageInfo)
    }
}