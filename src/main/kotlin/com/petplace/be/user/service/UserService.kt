package com.petplace.be.user.service

import com.petplace.be.auth.domain.JwtTokenProvider
import com.petplace.be.auth.external.GoogleAuthentication
import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.user.domain.User
import com.petplace.be.user.dto.SignUpResult
import com.petplace.be.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

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

        // TODO :: 구글 로그인 테스트 환경 구축 시 제거
        // val email = "test-sign-up-email"

        val user = User(
            email = email,
            nickname = nickname
        )

        val signedUpUser = userRepository.save(user)
        val accessToken: String = jwtTokenProvider.issueAccessToken(signedUpUser.id!!)
        val refreshToken: String = jwtTokenProvider.issueRefreshToken(signedUpUser.id!!)

        signedUpUser.refreshToken = refreshToken
        userRepository.save(signedUpUser)

        return SignUpResult(
            nickname = nickname,
            accessToken = accessToken,
            refreshToken = refreshToken,
            isSignedUpUser = true
        )
    }

    @Transactional(readOnly = true)
    fun findUserByEmail(email: String): Optional<User> {
        return userRepository.findByEmail(email)
    }

    @Transactional(readOnly = true)
    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw CommonException(ErrorCode.USER_NOT_FOUND) }
    }

    fun updateUser(user: User) {
        userRepository.save(user)
    }
}
