package com.th.plu.api.controller.member.dto.request

import com.th.plu.domain.domain.member.MemberSocialType

data class CreateUserRequestDto(
    val socialId: String,
    val socialType: MemberSocialType,
    val fcmToken: String,
    val nickname: String
)
