package com.th.plu.external.client.kakao

import com.th.plu.external.client.kakao.dto.response.KakaoProfileResponseDto

interface KakaoApiCaller {

    fun getProfileInfo(accessToken: String): KakaoProfileResponseDto
}
