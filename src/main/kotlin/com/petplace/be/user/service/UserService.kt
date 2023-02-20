package com.petplace.be.user.service

import com.petplace.be.auth.domain.JwtTokenProvider
import com.petplace.be.auth.external.GoogleAuthentication
import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.user.domain.User
import com.petplace.be.user.dto.SignUpResult
import com.petplace.be.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.random.Random

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Value("\${user.default-profile-image-url.prefix}")
    lateinit var DEFAULT_PROFILE_IMAGE_URL_PREFIX: String

    @Value("\${user.default-profile-image-url.extension}")
    lateinit var DEFAULT_PROFILE_IMAGE_URL_EXTENSION: String

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
            nickname = nickname,
            profileUrl = getRandomDefaultProfileImageUrl()
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
            isSignedUpUser = true,
            profileUrl = signedUpUser.profileUrl!!,
        )
    }

    private fun getRandomDefaultProfileImageUrl(): String {
        return "${DEFAULT_PROFILE_IMAGE_URL_PREFIX}${(Random.nextInt(5) + 1)}.${DEFAULT_PROFILE_IMAGE_URL_EXTENSION}"
    }

    @Transactional(readOnly = true)
    fun findUserByEmail(email: String): Optional<User> {
        return userRepository.findByEmail(email)
    }

    @Transactional(readOnly = true)
    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw CommonException(ErrorCode.USER_NOT_FOUND) }
    }

    fun updateUserNickname(newNickname: String) {
        val currentUser = getUserById(getCurrentUserId())
        if (currentUser.nickname == newNickname) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
        currentUser.nickname = newNickname
    }

    private fun getCurrentUserId(): Long {
        return SecurityContextHolder.getContext().authentication.principal as Long
    }

    fun deleteUser() {
        val currentUser = getUserById(getCurrentUserId())
        userRepository.delete(currentUser)
    }
}
