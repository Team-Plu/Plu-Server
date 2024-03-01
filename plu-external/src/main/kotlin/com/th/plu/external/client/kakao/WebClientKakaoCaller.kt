package com.th.plu.external.client.kakao

import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.BadGatewayException
import com.th.plu.common.exception.model.ValidationException
import com.th.plu.external.client.kakao.dto.response.KakaoProfileResponseDto
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class WebClientKakaoCaller(
    private val webClient: WebClient
) : KakaoApiCaller {

    override fun getProfileInfo(accessToken: String): KakaoProfileResponseDto {
        return webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .headers { it.setBearerAuth(accessToken) }
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) {
                throw ValidationException(
                    ErrorCode.VALIDATION_INVALID_TOKEN_EXCEPTION,
                    "잘못된 카카오 액세스 토큰 $accessToken 입니다."
                )
            }
            .onStatus(HttpStatusCode::is5xxServerError) {
                throw BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION, "카카오 로그인 연동 중 에러가 발생하였습니다.")
            }
            .bodyToMono(KakaoProfileResponseDto::class.java)
            .block()!!
    }
}
