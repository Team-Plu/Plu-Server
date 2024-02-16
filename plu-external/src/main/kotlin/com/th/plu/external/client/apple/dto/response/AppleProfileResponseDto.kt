package com.th.plu.external.client.apple.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.BadGatewayException

@JsonIgnoreProperties(ignoreUnknown = true)
data class AppleProfileResponseDto(
    val keys: List<Key>
) {
    class Key {
        val alg: String? = null
        val e: String? = null
        val kid: String? = null
        val kty: String? = null
        val n: String? = null
        val use: String? = null
    }

    fun getMatchedPublicKey(kid: String, alg: String): Key {
        return try {
            this.keys.first { it.kid.equals(kid) && it.alg.equals(alg) }
        } catch (_: NoSuchElementException) {
            throw BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION, "애플 로그인 연동 중 에러가 발생하였습니다.")
        }
    }
}
