package com.th.plu.domain.member

import lombok.Getter

@Getter
enum class MemberRole(private val value: String) {
    ADMIN("관리자"),
    MEMBER("회원")
}
