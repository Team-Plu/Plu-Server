package com.th.plu.external.client.apple

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.UnauthorizedException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Component
class AppleTokenProvider(
    private val appleApiCaller: AppleApiCaller,
    private val objectMapper: ObjectMapper
) {

    fun getSocialIdFromIdToken(idToken: String): String {
        val headerIdToken = idToken.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        return try {
            val header = objectMapper.readValue(
                String(Base64.getDecoder().decode(headerIdToken), StandardCharsets.UTF_8),
                object : TypeReference<Map<String, String>>() {})
            val publicKey: PublicKey = getPublicKey(header)
            val claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(idToken)
                .body
            claims.subject // return socialId
        } catch (e: Exception) {
            throw UnauthorizedException(
                ErrorCode.UNAUTHORIZED_EXCEPTION,
                "잘못된 애플 idToken $idToken 입니다 (reason: ${e.message})"
            )
        }
    }

    private fun getPublicKey(header: Map<String, String>): PublicKey {
        val response = appleApiCaller.getProfileInfo()
        val key = response.getMatchedPublicKey(header["kid"].toString(), header["alg"].toString())
        val nBytes: ByteArray = Base64.getUrlDecoder().decode(key.n)
        val eBytes: ByteArray = Base64.getUrlDecoder().decode(key.e)
        val nBigInt = BigInteger(1, nBytes)
        val eBigInt = BigInteger(1, eBytes)
        val publicKeySpec = RSAPublicKeySpec(nBigInt, eBigInt)
        val keyFactory = KeyFactory.getInstance(key.kty)
        return keyFactory.generatePublic(publicKeySpec)
    }
}
