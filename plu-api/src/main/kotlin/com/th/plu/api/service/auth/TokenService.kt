package com.th.plu.api.service.auth

import com.th.plu.api.controller.auth.dto.request.TokenRequestDto
import com.th.plu.api.controller.auth.dto.response.TokenResponseDto
import com.th.plu.api.service.member.MemberServiceUtils
import com.th.plu.common.constant.RedisKey
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.UnauthorizedException
import com.th.plu.common.util.JwtUtils
import com.th.plu.domain.domain.member.repository.MemberRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenService(
    private val jwtUtils: JwtUtils,
    private val memberRepository: MemberRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    @Transactional
    fun createTokenInfo(memberId: Long): TokenResponseDto {
        val tokens: List<String> = jwtUtils.createTokenInfo(memberId)
        return TokenResponseDto(accessToken = tokens[0], refreshToken = tokens[1])
    }

    @Transactional
    fun reissueToken(request: TokenRequestDto): TokenResponseDto {
        val memberId = jwtUtils.getMemberIdFromJwt(request.accessToken)
        val member = MemberServiceUtils.findMemberById(memberRepository, memberId)
        if (!jwtUtils.validateToken(request.refreshToken)) {
            member.resetFcmToken()
            throw UnauthorizedException(
                ErrorCode.UNAUTHORIZED_EXCEPTION,
                "주어진 리프레시 토큰 ${request.refreshToken} 이 유효하지 않습니다."
            )
        }
        val refreshToken = redisTemplate.opsForValue().get(RedisKey.REFRESH_TOKEN + memberId) as String?
        if (refreshToken == null) {
            member.resetFcmToken()
            throw UnauthorizedException(
                ErrorCode.UNAUTHORIZED_EXCEPTION,
                "이미 만료된 리프레시 토큰 ${request.refreshToken} 입니다."
            )
        }
        if (refreshToken != request.refreshToken) {
            jwtUtils.expireRefreshToken(member.id!!)
            member.resetFcmToken()
            throw UnauthorizedException(
                ErrorCode.UNAUTHORIZED_EXCEPTION,
                "해당 리프레시 토큰 ${request.refreshToken} 의 정보가 일치하지 않습니다."
            )
        }
        val newTokens = jwtUtils.createTokenInfo(memberId)
        return TokenResponseDto(accessToken = newTokens[0], refreshToken = newTokens[1])
    }
}
