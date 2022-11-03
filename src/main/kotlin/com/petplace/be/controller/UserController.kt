package com.petplace.be.controller

import com.petplace.be.response.BaseResponse
import com.petplace.be.user.UserService
import com.petplace.be.user.param.UserUpdateParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PutMapping("/user")
    fun updateUser(@RequestBody param: UserUpdateParam): BaseResponse<Void>{
        userService.updateUser(param)

        return BaseResponse()
    }
}