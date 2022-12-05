package com.petplace.be.auth.controller

import com.petplace.be.auth.dto.SignInParam
import com.petplace.be.auth.dto.SignInResult
import com.petplace.be.auth.service.AuthService
import com.petplace.be.common.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    // TODO :: refresh token 로직 구현
//    @PostMapping("/refresh")
//    fun refresh(@RequestBody param: ReLoginParam): BaseResponse<LoginResult> {
//        var result = loginService.regenerateToken(param);
//        return BaseResponse(data = result)
//    }
}
