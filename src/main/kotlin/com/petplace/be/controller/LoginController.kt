package com.petplace.be.controller

import com.petplace.be.login.LoginService
import com.petplace.be.login.param.LoginParam
import com.petplace.be.login.param.ReLoginParam
import com.petplace.be.login.result.LoginResult
import com.petplace.be.response.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController {
    @Autowired
    private lateinit var loginService: LoginService

    @PostMapping("/google")
    fun login(@RequestBody param: LoginParam): BaseResponse<LoginResult> {
        val result = loginService.loginWithGoogle(param.idToken)

        return BaseResponse(data = result)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody param: ReLoginParam): BaseResponse<LoginResult> {
        //
        var result = loginService.regenerateToken(param);

        return BaseResponse(data = result)
    }

    @PostMapping("/test")
    fun test(): BaseResponse<String> {
        return BaseResponse("test")
    }
}
