package com.petplace.be.controller

import com.petplace.be.response.BaseResponse
import com.petplace.be.user.UserService
import com.petplace.be.user.param.UserUpdateParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpParam: SignUpParam): ResponseEntity<Void>{
        userService.signUp(signUpParam.googleIdToken, signUpParam.nickname)
        return ResponseEntity.ok().build()
    }

    @PutMapping
    fun updateUser(@RequestBody param: UserUpdateParam): BaseResponse<Void> {
        userService.updateUser(param)

        return BaseResponse()
    }
}
