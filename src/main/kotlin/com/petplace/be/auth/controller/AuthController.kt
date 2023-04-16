package com.petplace.be.auth.controller

import com.petplace.be.auth.dto.RefreshAccessTokenParam
import com.petplace.be.auth.dto.RefreshAccessTokenResult
import com.petplace.be.auth.dto.SignInParam
import com.petplace.be.auth.dto.SignInResult
import com.petplace.be.auth.service.AuthService
import com.petplace.be.common.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @Operation(summary = "구글 로그인", description = "구글을 통해 로그인을 합니다.")
    @PostMapping("/google/sign-in")
    fun login(@RequestBody signInParam: SignInParam): BaseResponse<SignInResult> {
        val result = authService.loginWithGoogle(signInParam.idToken)
        return BaseResponse(result)
    }

    @Operation(summary = "로그인 액세스 토큰 생성", description = "새 로그인 액세스 토큰을 생성합니다.")
    @PostMapping("/access-token")
    fun refreshAccessToken(@RequestBody refreshAccessTokenParam: RefreshAccessTokenParam): BaseResponse<RefreshAccessTokenResult> {
        val result = authService.refreshAccessToken(refreshAccessTokenParam.refreshToken)
        return BaseResponse(result)
    }
}
