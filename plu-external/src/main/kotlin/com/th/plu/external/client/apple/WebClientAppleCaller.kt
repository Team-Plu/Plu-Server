package com.th.plu.external.client.apple

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.BadGatewayException
import com.th.plu.external.client.apple.dto.response.AppleProfileResponseDto
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class WebClientAppleCaller(
    private val webClient: WebClient
) : AppleApiCaller {

    override fun getProfileInfo(): AppleProfileResponseDto {
        return webClient.get()
            .uri("https://appleid.apple.com/auth/keys")
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) {
                throw BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION, "애플 로그인 연동 중 에러가 발생하였습니다.")
            }
            .onStatus(HttpStatusCode::is5xxServerError) {
                throw BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION, "애플 로그인 연동 중 에러가 발생하였습니다.")
            }
            .bodyToMono(AppleProfileResponseDto::class.java)
            .block()!!
    }
}
