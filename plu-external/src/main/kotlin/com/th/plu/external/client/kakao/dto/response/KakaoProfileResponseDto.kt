package com.th.plu.external.client.kakao.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoProfileResponseDto(
    val id: String
)
