package com.petplace.be.auth.controller

import com.petplace.be.auth.dto.RefreshAccessTokenParam
import com.petplace.be.auth.dto.RefreshAccessTokenResult
import com.petplace.be.auth.dto.SignInParam
import com.petplace.be.auth.dto.SignInResult
import com.petplace.be.auth.service.AuthService
import com.petplace.be.common.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-in/google")
    fun login(@RequestBody signInParam: SignInParam): BaseResponse<SignInResult> {
        val result = authService.loginWithGoogle(signInParam.idToken)
        return BaseResponse(result)
    }

    @PutMapping("/access-token")
    fun refreshAccessToken(@RequestBody refreshAccessTokenParam: RefreshAccessTokenParam): BaseResponse<RefreshAccessTokenResult> {
        val result = authService.refreshAccessToken(refreshAccessTokenParam.refreshToken)
        return BaseResponse(result)
    }
}
