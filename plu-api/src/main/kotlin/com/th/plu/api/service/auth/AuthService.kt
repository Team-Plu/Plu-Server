package com.th.plu.api.service.auth

import com.th.plu.api.controller.auth.dto.request.LoginRequestDto
import com.th.plu.api.controller.auth.dto.request.SignupRequestDto

interface AuthService {

    fun signup(request: SignupRequestDto): Long
    fun login(request: LoginRequestDto): Long

}