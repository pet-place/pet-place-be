package com.petplace.be.user.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.user.dto.SignUpParam
import com.petplace.be.user.dto.SignUpResult
import com.petplace.be.user.dto.UpdateNickNameParam
import com.petplace.be.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "회원가입", description = "새 회원을 등록합니다.")
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpParam: SignUpParam): BaseResponse<SignUpResult> {
        return BaseResponse(userService.signUp(signUpParam.googleIdToken, signUpParam.nickname))
    }

    @Operation(summary = "회원 닉네임 수정", description = "등록된 회원의 닉네임을 수정합니다.")
    @PutMapping("/nickname")
    fun updateUserNickname(@RequestBody updateNickNameParam: UpdateNickNameParam): BaseResponse<Void> {
        userService.updateUserNickname(updateNickNameParam.newNickName)
        return BaseResponse()
    }

    @Operation(summary = "회원 탈퇴", description = "등록된 회원을 삭제합니다.")
    @DeleteMapping
    fun deleteUser(): BaseResponse<Void> {
        userService.deleteUser()
        return BaseResponse()
    }

    // TODO :: 인증 로직 개발 완료 시 제거, 시큐리터 필터 동작 테스트용
    @GetMapping("/test")
    fun test(): BaseResponse<String> {
        return BaseResponse("test")
    }
}
