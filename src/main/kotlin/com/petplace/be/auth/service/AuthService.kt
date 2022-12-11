package com.petplace.be.auth.service

import com.petplace.be.auth.domain.JwtTokenProvider
import com.petplace.be.auth.dto.RefreshAccessTokenResult
import com.petplace.be.auth.dto.SignInResult
import com.petplace.be.auth.external.GoogleAuthentication
import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun loginWithGoogle(idToken: String): SignInResult {
        val googleIdToken =
            GoogleAuthentication.verifierIdToken(idToken) ?: throw CommonException(ErrorCode.INVALID_GOOGLE_ID_TOKEN)
        val email = googleIdToken.payload.email
        val initialNickname = googleIdToken.payload["name"] as String

        // TODO :: 구글 로그인 테스트 환경 구축 시 제거
        // val email = "test-sign-up-email"
        // val initialNickname = "initial-nickname"
        val user = userService.findUserByEmail(email)

        if (user.isEmpty) {
            return SignInResult(
                nickname = initialNickname,
                accessToken = null,
                refreshToken = null,
                isSignedUpUser = false
            )
        }

        val signedUpUser = user.get()
        val accessToken: String = jwtTokenProvider.issueAccessToken(signedUpUser.id!!)
        val refreshToken: String = jwtTokenProvider.issueRefreshToken(signedUpUser.id!!)

        signedUpUser.refreshToken = refreshToken;
        userService.updateUser(user.get())

        return SignInResult(
            nickname = user.get().nickname!!,
            accessToken = accessToken,
            refreshToken = refreshToken,
            isSignedUpUser = true
        )
    }

    fun refreshAccessToken(refreshToken: String): RefreshAccessTokenResult {
        jwtTokenProvider.validateToken(refreshToken)

        val userId: Long = jwtTokenProvider.getUserIdFromToken(refreshToken)!!.toLong()
        val user = userService.getUserById(userId)

        if (refreshToken != user.refreshToken) {
            println(refreshToken)
            println(user.refreshToken)
            throw CommonException(ErrorCode.REFRESH_TOKEN_NOT_MATCHED)
        }

        val newAccessToken = jwtTokenProvider.issueAccessToken(userId)
        val newRefreshToken = jwtTokenProvider.issueRefreshToken(userId)

        user.refreshToken = newRefreshToken
        userService.updateUser(user)

        return RefreshAccessTokenResult(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }
}
