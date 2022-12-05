package com.petplace.be.auth.service

import com.petplace.be.auth.domain.JwtTokenProvider
import com.petplace.be.auth.domain.UserAuthentication
import com.petplace.be.auth.dto.SignInResult
import com.petplace.be.auth.external.GoogleAuthentication
import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.user.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional(readOnly = true)
    fun loginWithGoogle(idToken: String): SignInResult {
        val googleIdToken =
            GoogleAuthentication.verifierIdToken(idToken) ?: throw CommonException(ErrorCode.INVALID_GOOGLE_ID_TOKEN)
        val email = googleIdToken.payload.email
        val initialNickname = googleIdToken.payload["name"] as String

        // TODO :: 구글 로그인 테스트 환경 구축 시 제거
        // val email = "test-sign-up-email"
        // val initialNickname = "initial-nickname"
        val user = userRepository.findByEmail(email)

        if (user.isEmpty) {
            return SignInResult(
                nickname = initialNickname,
                accessToken = null,
                isSignedUpUser = false
            )
        }

        val authentication: Authentication = UserAuthentication(
            user.get().id,
            null,
            null
        )
        val accessToken: String = jwtTokenProvider.generateAccessToken(authentication)

        return SignInResult(
            nickname = user.get().nickname!!,
            accessToken = accessToken,
            isSignedUpUser = true
        )
    }

//    @Transactional
//    fun regenerateToken(param: ReLoginParam): LoginResult {
//        var newRefreshToken = param.refreshToken
//
//        // refresh token 으로 사용자 조회
//        val user: User = userRepository.findByRefreshToken(param.refreshToken)
//            .orElseThrow { throw CommonException(ErrorCode.USER_NOT_FOUND) } // 예외 처리 ..
//
//        // DB에 저장된 refresh token 이 없으면 새로 갱신
//        if (user.refreshToken == null) {
//            newRefreshToken = jwtTokenProvider.generateRefreshToken()
//        }
//        // token 유효성 검사
//        else if (jwtTokenProvider.validateToken(param.refreshToken)) {
//            // 남은 만료기간이 3일 이하일 때 refresh token 갱신
//            newRefreshToken = getExpireTimeRemaining(param.refreshToken)
//        }
//
//        // access token 갱신
//        val authentication: Authentication = UserAuthentication(
//            param.userId,
//            null,
//            null
//        )
//        var newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
//
//        user.updateToken(newAccessToken, newRefreshToken);
//
//        return LoginResult(
//            nickName = user.nickname!!,
//            accessToken = user.accessToken!!,
//            refreshToken = user.refreshToken!!,
//        )
//    }
}
