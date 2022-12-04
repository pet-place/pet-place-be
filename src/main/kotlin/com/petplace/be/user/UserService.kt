package com.petplace.be.user

import com.google.api.client.auth.openidconnect.IdToken.Payload
import com.petplace.be.config.oauth.GoogleAuthentication
import com.petplace.be.constract.ErrorCode
import com.petplace.be.entity.User
import com.petplace.be.exception.CommonException
import com.petplace.be.user.param.UserUpdateParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Transactional
    fun signUp(googleIdToken: String, nickname: String) {
        if (userRepository.existsByNickname(nickname)) {
            throw CommonException(ErrorCode.DUPLICATED_NICKNAME)
        }

//        val token = GoogleAuthentication.verifierIdToken(googleIdToken) ?: throw CommonException(ErrorCode.INVALID_ID_TOKEN)
//        val payload = token.payload

        val user = User(
            // TODO :: 구글 로그인 테스트 환경 구축 시 변경
            // email = payload.email,
            email = "test-sign-up-email",
            nickname = nickname
        )

        userRepository.save(user)
    }

    fun findByEmail(email: String) {
        userRepository.findByEmail(email)
    }

//    fun updateUser(param: UserUpdateParam) {
//        var user: User = userRepository.findById(param.id).orElseThrow { throw CommonException(ErrorCode.NOT_FOUND_USER) }
//
//        if (existByNickname(param)) throw CommonException(ErrorCode.DUPLICATED_NICKNAME)
//
//        user.nickname = param.nickName
//        userRepository.save(user)
//    }

    fun existByNickname(param: UserUpdateParam): Boolean {
        return userRepository.existsByNickname(param.nickName)
    }
}
