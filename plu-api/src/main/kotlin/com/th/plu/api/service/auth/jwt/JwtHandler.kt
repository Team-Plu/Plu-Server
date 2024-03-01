package com.th.plu.api.service.auth.jwt

import com.th.plu.api.service.redis.RedisHandler
import com.th.plu.common.constant.JwtKey
import com.th.plu.common.constant.RedisKey
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtHandler(
    private val redisHandler: RedisHandler,
) {
    @Value("\${jwt.secret}")
    private var jwtSecret: String? = null
    private var secretKey: Key? = null
    private val log = LoggerFactory.getLogger(this.javaClass)

    companion object {
        // private const val ACCESS_TOKEN_EXPIRE_TIME = 10 * 60 * 1000L // 10분
        // private const val REFRESH_TOKEN_EXPIRE_TIME = 6 * 30 * 24 * 60 * 60 * 1000L // 180일
        private const val ACCESS_TOKEN_EXPIRE_TIME = 365 * 24 * 60 * 60 * 1000L;   // 1년
        private const val REFRESH_TOKEN_EXPIRE_TIME = 365 * 24 * 60 * 60 * 1000L;    // 1년
        private const val EXPIRED_TIME = 1L
    }

    @PostConstruct
    fun init() {
        val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSecret)
        this.secretKey = Keys.hmacShaKeyFor(keyBytes)
    }

    fun createTokenInfo(memberId: Long): TokenDto {
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

        redisHandler.set(RedisKey.REFRESH_TOKEN + memberId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME)

        return TokenDto(accessToken, refreshToken)
    }

    fun expireRefreshToken(memberId: Long) {
        redisHandler.delete(RedisKey.REFRESH_TOKEN + memberId)
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

    fun getMemberIdFromJwt(accessToken: String): Long? {
        val memberId = parseClaims(accessToken)[JwtKey.MEMBER_ID] as Int?
        return memberId?.toLong()
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}
