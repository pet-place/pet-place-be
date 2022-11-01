package com.petplace.be.login

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.petplace.be.config.jwt.JwtTokenProvider
import com.petplace.be.config.jwt.UserAuthentication
import com.petplace.be.config.oauth.GoogleAuthentication
import com.petplace.be.entity.User
import com.petplace.be.login.result.LoginResult
import com.petplace.be.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class LoginService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun loginWithGoogle(idToken: String): LoginResult {

        // idToken 검증
        val googleIdToken = GoogleAuthentication.verifierIdToken(idToken) ?: throw Exception()

        var payload = googleIdToken.payload
        var result: LoginResult? = null

        // 최초 로그인이면 사용자 저장
        if (isFirstLoginUser(payload)) {
            var user = User()
            user.nickname = (payload["name"] as String?)!!
            user.email = payload.email

            // TODO access_token 생성 후 저장
            var resultUser: User = userRepository.save(user)
            val authentication: Authentication = UserAuthentication(
                resultUser.id.toString(),
                null,
                null)
            var accessToken: String = JwtTokenProvider.generateToken(authentication)

            result = LoginResult(
                id = resultUser.id!!,
                nickName = resultUser.nickname,
                accessToken = accessToken,
                isFirstLogin = true
            )
        } else {
            val user = userRepository.findByEmail(payload.email).orElseThrow()
            result = LoginResult(
                id = user.id!!,
                nickName = user.nickname,
                accessToken = user.accessToken,
                isFirstLogin = false
            )
        }
        return result
    }

    private fun isFirstLoginUser(payload: GoogleIdToken.Payload): Boolean {
        val email = payload.email
        return !userRepository.existsByEmail(email)
    }
}