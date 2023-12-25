package com.th.plu.domain.member

import lombok.AccessLevel
import lombok.Getter
import lombok.RequiredArgsConstructor

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
enum class MemberRole(private val value: String) {
    ADMIN("관리자"),
    MEMBER("회원")
}
