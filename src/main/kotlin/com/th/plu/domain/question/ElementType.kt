package com.th.plu.domain.question

enum class ElementType(
    private val characterImageUrl: String,
    private val elementImageUrl: String,
    private val colorCode: String
) {
    // TODO: 엘리먼트 네이밍 체크 필요
    WATER("", "", ""),
    FIRE("", "", ""),
    GROUND("", "", ""),
    CLOUD("", "", "")
}
