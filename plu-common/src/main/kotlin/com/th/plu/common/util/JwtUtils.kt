package com.th.plu.common.util

import com.th.plu.common.constant.JwtKey
import com.th.plu.common.constant.RedisKey
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class JwtUtils(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    @Value("\${jwt.secret}")
    private var jwtSecret: String? = null

    private var secretKey: Key? = null
    private val log = LoggerFactory.getLogger(this.javaClass)

    companion object {
//        private const val ACCESS_TOKEN_EXPIRE_TIME = 10 * 60 * 1000L // 10분
//        private const val REFRESH_TOKEN_EXPIRE_TIME = 6 * 30 * 24 * 60 * 60 * 1000L // 180일

        private const val ACCESS_TOKEN_EXPIRE_TIME = 365 * 24 * 60 * 60 * 1000L;   // 1년
        private const val REFRESH_TOKEN_EXPIRE_TIME = 365 * 24 * 60 * 60 * 1000L;    // 1년
        private const val EXPIRED_TIME = 1L
    }

    @PostConstruct
    fun init() {
        val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSecret)
        this.secretKey = Keys.hmacShaKeyFor(keyBytes)
    }

    fun createTokenInfo(memberId: Long): List<String> {
        val now = Date().time
        val accessTokenExpiresIn = Date(now + ACCESS_TOKEN_EXPIRE_TIME)
        val refreshTokenExpiresIn = Date(now + REFRESH_TOKEN_EXPIRE_TIME)

        // Access Token 생성
        val accessToken: String = Jwts.builder()
            .claim(JwtKey.MEMBER_ID, memberId)
            .setExpiration(accessTokenExpiresIn)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()

        // Refresh Token 생성
        val refreshToken: String = Jwts.builder()
            .setExpiration(refreshTokenExpiresIn)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()

        redisTemplate.opsForValue()
            .set(RedisKey.REFRESH_TOKEN + memberId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS)

        return listOf(accessToken, refreshToken)
    }

    fun expireRefreshToken(memberId: Long) {
        redisTemplate.opsForValue().set(RedisKey.REFRESH_TOKEN + memberId, "", EXPIRED_TIME, TimeUnit.MILLISECONDS)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.warn("Invalid JWT Token", e)
        } catch (e: MalformedJwtException) {
            log.warn("Invalid JWT Token", e)
        } catch (e: DecodingException) {
            log.warn("Invalid JWT Token", e)
        } catch (e: ExpiredJwtException) {
            log.warn("Expired JWT Token", e)
        } catch (e: UnsupportedJwtException) {
            log.warn("Unsupported JWT Token", e)
        } catch (e: IllegalArgumentException) {
            log.warn("JWT claims string is empty.", e)
        } catch (e: Exception) {
            log.error("Unhandled JWT exception", e)
        }
        return false
    }

    fun getMemberIdFromJwt(accessToken: String): Long {
        return parseClaims(accessToken).get(JwtKey.MEMBER_ID, Long::class.java)
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}
