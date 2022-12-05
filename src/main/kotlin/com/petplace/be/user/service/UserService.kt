package com.petplace.be.user.service

import com.petplace.be.auth.domain.JwtTokenProvider
import com.petplace.be.auth.domain.UserAuthentication
import com.petplace.be.auth.external.GoogleAuthentication
import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.user.domain.User
import com.petplace.be.user.dto.SignUpResult
import com.petplace.be.user.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun signUp(googleIdToken: String, nickname: String): SignUpResult {
        if (userRepository.existsByNickname(nickname)) {
            throw CommonException(ErrorCode.DUPLICATE_NICKNAME)
        }

        val token = GoogleAuthentication.verifierIdToken(googleIdToken)
                ?: throw CommonException(ErrorCode.INVALID_GOOGLE_ID_TOKEN)
        val email = token.payload.email

        val user = User(
            // TODO :: 구글 로그인 테스트 환경 구축 시 제거
            // email = "test-sign-up-email",
            email = email,
            nickname = nickname
        )

        val signedUpUser = userRepository.save(user)

        val authentication: Authentication = UserAuthentication(
            signedUpUser.id,
            null,
            null
        )
        val accessToken: String = jwtTokenProvider.generateAccessToken(authentication)

        return SignUpResult(
            nickname = nickname,
            accessToken = accessToken,
            isSignedUpUser = true
        )
    }

//    fun updateUser(param: UserUpdateParam) {
//        var user: User = userRepository.findById(param.id).orElseThrow { throw CommonException(ErrorCode.NOT_FOUND_USER) }
//
//        if (existByNickname(param)) throw CommonException(ErrorCode.DUPLICATED_NICKNAME)
//
//        user.nickname = param.nickName
//        userRepository.save(user)
//    }

//    fun existByNickname(param: UserUpdateParam): Boolean {
//        return userRepository.existsByNickname(param.nickName)
//    }
}
