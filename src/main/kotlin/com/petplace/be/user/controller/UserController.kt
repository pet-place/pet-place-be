package com.petplace.be.user.controller

import com.petplace.be.common.BaseResponse
import com.petplace.be.user.dto.SignUpParam
import com.petplace.be.user.dto.SignUpResult
import com.petplace.be.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpParam: SignUpParam): BaseResponse<SignUpResult> {
        return BaseResponse(userService.signUp(signUpParam.googleIdToken, signUpParam.nickname))
    }

    // TODO :: 인증 로직 개발 완료 시 제거, 시큐리터 필터 동작 테스트용
    @GetMapping("/test")
    fun test(): BaseResponse<String> {
        return BaseResponse("test")
    }

//    @PutMapping
//    fun updateUser(@RequestBody param: UserUpdateParam): BaseResponse<Void> {
//        userService.updateUser(param)
//
//        return BaseResponse()
//    }
}
