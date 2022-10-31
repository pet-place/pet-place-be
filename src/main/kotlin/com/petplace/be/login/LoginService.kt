package com.petplace.be.login

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.petplace.be.config.token.JwtTokenProvider
import com.petplace.be.config.token.UserAuthentication
import com.petplace.be.constract.ErrorCode
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
        val googleIdToken = verifierIdToken(idToken) ?: throw Exception()

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

    // idToken 유효성 검증
    private fun verifierIdToken(idToken: String): GoogleIdToken? {
        val transport: HttpTransport = NetHttpTransport()
        val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
        val verifier =
            GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    //TODO property 로 빼기
                .setAudience(listOf("663371736991-3gjbva9d64mplielt3m14ji527q4ihad.apps.googleusercontent.com"))
                .build()
        return verifier.verify(idToken)
    }

    private fun isFirstLoginUser(payload: GoogleIdToken.Payload): Boolean {
        val email = payload.email
        return !userRepository.existsByEmail(email)
    }
}