package com.th.plu.external.client.apple

import com.th.plu.external.client.apple.dto.response.AppleProfileResponseDto

interface AppleApiCaller {

    fun getProfileInfo(): AppleProfileResponseDto
}
