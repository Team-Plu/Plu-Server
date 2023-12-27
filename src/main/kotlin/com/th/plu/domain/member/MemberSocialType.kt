package com.th.plu.domain.member

import lombok.Getter

@Getter
enum class MemberSocialType(private val value: String) {
    KAKAO("카카오톡"),
    APPLE("애플");
}
