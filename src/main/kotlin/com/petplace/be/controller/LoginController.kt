package com.petplace.be.controller

import com.petplace.be.login.LoginService
import com.petplace.be.login.param.LoginParam
import com.petplace.be.login.result.LoginResult
import com.petplace.be.response.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {
    @Autowired
    private lateinit var loginService: LoginService

    @PostMapping("/login/google")
    fun login(@RequestBody param: LoginParam): BaseResponse<LoginResult> {
        val result = loginService.loginWithGoogle(param.idToken)

        return BaseResponse(data = result)
    }
}