package com.th.plu.api.config.resolver

import com.th.plu.api.config.interceptor.Auth
import com.th.plu.common.constant.JwtKey
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.InternalServerException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class MemberIdResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(MemberId::class.java) && Long::class.java == parameter.parameterType
    }

    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
    ): Any? {
        parameter.getMethodAnnotation(Auth::class.java)
            ?: throw InternalServerException(
                ErrorCode.INTERNAL_SERVER_EXCEPTION,
                "인증이 필요한 컨트롤러 입니다. @Auth 어노테이션을 붙여주세요."
            )

        return webRequest.getAttribute(JwtKey.MEMBER_ID, 0)
            ?: throw InternalServerException(
                ErrorCode.INTERNAL_SERVER_EXCEPTION,
                "MEMBER_ID 를 가져오지 못했습니다. ($parameter.javaClass - $parameter.method)"
            )
    }
}
