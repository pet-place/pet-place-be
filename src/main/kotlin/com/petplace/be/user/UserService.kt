package com.petplace.be.user

import com.petplace.be.constract.ErrorCode
import com.petplace.be.entity.User
import com.petplace.be.user.param.UserUpdateParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun findByEmail(email: String) {
        userRepository.findByEmail(email)
    }

    fun updateUser(param: UserUpdateParam){
        var user: User = userRepository.findById(param.id).orElseThrow()

        if (existByNickname(param)) throw IllegalStateException(ErrorCode.DUPLICATED_NICKNAME.message)

        user.nickname = param.nickName
        userRepository.save(user)
    }

    fun existByNickname(param: UserUpdateParam): Boolean{
        return userRepository.existsByNicknameAndId(param.nickName, param.id)
    }
}