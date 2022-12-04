package com.petplace.be.login

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.petplace.be.config.jwt.JwtTokenProvider
import com.petplace.be.config.jwt.UserAuthentication
import com.petplace.be.config.oauth.GoogleAuthentication
import com.petplace.be.constract.ErrorCode
import com.petplace.be.entity.User
import com.petplace.be.exception.CommonException
import com.petplace.be.login.param.ReLoginParam
import com.petplace.be.login.result.LoginResult
import com.petplace.be.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Transactional
    fun loginWithGoogle(idToken: String): LoginResult {
        // idToken 검증
        val googleIdToken = GoogleAuthentication.verifierIdToken(idToken) ?: throw CommonException(ErrorCode.INVALID_ID_TOKEN)
        val payload = googleIdToken.payload
        var result: LoginResult? = null

        // 최초 로그인이면 사용자 저장
        if (isFirstLoginUser(payload)) {
            var user = User(
                    nickname = payload["name"] as String,
                    email = payload.email
            )

            // TODO access_token 생성 후 저장
            var resultUser: User = userRepository.save(user)
            val authentication: Authentication = UserAuthentication(
                    resultUser.id.toString(),
                    null,
                    null)

            var accessToken: String = jwtTokenProvider.generateAccessToken(authentication)
            var refreshToken: String = jwtTokenProvider.generateRefreshToken();

            user.updateToken(accessToken, refreshToken)

            result = LoginResult(
                    nickName = resultUser.nickname ?: "",
                    accessToken = accessToken,
                    isFirstLogin = true,
                    refreshToken = refreshToken
            )
        } else {
            val user = userRepository.findByEmail(payload.email).orElseThrow { throw CommonException(ErrorCode.NOT_FOUND_USER) }
            result = LoginResult(
                    nickName = user.nickname ?: "",
                    accessToken = user.accessToken ?: "",
                    isFirstLogin = false,
                    refreshToken = user.refreshToken ?: ""
            )
        }
        return result
    }

    private fun isFirstLoginUser(payload: GoogleIdToken.Payload): Boolean {
        val email = payload.email
        return !userRepository.existsByEmail(email)
    }

    @Transactional
    fun regenerateToken(param: ReLoginParam): LoginResult {
        var newRefreshToken = param.refreshToken

        // refresh token 으로 사용자 조회
        val user: User = userRepository.findByRefreshToken(param.refreshToken).orElseThrow { throw CommonException(ErrorCode.NOT_FOUND_USER) } // 예외 처리 ..

        // DB에 저장된 refresh token 이 없으면 새로 갱신
        if (user.refreshToken == null) {
            newRefreshToken = jwtTokenProvider.generateRefreshToken()
        }
        // token 유효성 검사
        else if (jwtTokenProvider.validateToken(param.refreshToken)) {
            // 남은 만료기간이 3일 이하일 때 refresh token 갱신
            newRefreshToken = getExpireTimeRemaining(param.refreshToken)
        }

        // access token 갱신
        val authentication: Authentication = UserAuthentication(
                param.userId,
                null,
                null)
        var newAccessToken = jwtTokenProvider.generateAccessToken(authentication);

        user.updateToken(newAccessToken, newRefreshToken);

        return LoginResult(
                nickName = user.nickname!!,
                accessToken = user.accessToken!!,
                refreshToken = user.refreshToken!!,
                isFirstLogin = false
        )
    }

    fun getExpireTimeRemaining(refreshToken: String): String {
        var decodedJwt: DecodedJWT = JWT.decode(refreshToken)
        var now = System.currentTimeMillis()
        val refreshExpireTime: Long = decodedJwt.getClaim("exp").asLong() * 1000
        var diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);

        // 남은 만료 기간이 3일 이하면
        if (diffDays < 3) {
            return jwtTokenProvider.generateRefreshToken()
        }

        return refreshToken
    }
}
