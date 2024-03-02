package com.th.plu.api.service.auth.jwt

import com.th.plu.api.controller.auth.dto.request.TokenRequestDto
import com.th.plu.api.controller.auth.dto.response.TokenResponseDto
import com.th.plu.api.service.redis.RedisHandler
import com.th.plu.common.constant.RedisKey
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.UnauthorizedException
import com.th.plu.domain.domain.member.explorer.MemberExplorer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenService(
    private val jwtHandler: JwtHandler,
    private val memberExplorer: MemberExplorer,
    private val redisHandler: RedisHandler
) {
    fun createTokenInfo(memberId: Long): TokenResponseDto {
        val tokens = jwtHandler.createTokenInfo(memberId)
        return tokens.toResponseDto()
    }

    @Transactional
    fun reissueToken(request: TokenRequestDto): TokenResponseDto {
        val memberId = jwtHandler.getMemberIdFromJwt(request.accessToken)
        val member = memberExplorer.findMemberById(memberId)
        if (!jwtHandler.validateToken(request.refreshToken)) {
            member.resetFcmToken()
            throw UnauthorizedException(
                ErrorCode.UNAUTHORIZED_EXCEPTION,
                "주어진 리프레시 토큰 ${request.refreshToken} 이 유효하지 않습니다."
            )
        }
        val refreshToken = redisHandler.get(RedisKey.REFRESH_TOKEN + memberId)
        if (refreshToken == null) {
            member.resetFcmToken()
            throw UnauthorizedException(
                ErrorCode.UNAUTHORIZED_EXCEPTION,
                "이미 만료된 리프레시 토큰 ${request.refreshToken} 입니다."
            )
        }
        if (refreshToken != request.refreshToken) {
            jwtHandler.expireRefreshToken(member.id!!)
            member.resetFcmToken()
            throw UnauthorizedException(
                ErrorCode.UNAUTHORIZED_EXCEPTION,
                "해당 리프레시 토큰 ${request.refreshToken} 의 정보가 일치하지 않습니다."
            )
        }
        val newTokens = jwtHandler.createTokenInfo(memberId)
        return newTokens.toResponseDto()
    }
}
