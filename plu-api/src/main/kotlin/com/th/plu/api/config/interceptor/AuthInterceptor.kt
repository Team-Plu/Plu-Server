package com.th.plu.api.config.interceptor

import com.th.plu.common.constant.JwtKey
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(
    private val loginCheckHandler: LoginCheckHandler
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }
        handler.getMethodAnnotation(Auth::class.java) ?: return true
        val memberId = loginCheckHandler.getMemberId(request)
        request.setAttribute(JwtKey.MEMBER_ID, memberId)
        return true
    }
}
