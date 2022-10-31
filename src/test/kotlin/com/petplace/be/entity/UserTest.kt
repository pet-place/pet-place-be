package com.petplace.be.entity

import com.petplace.be.config.token.JwtTokenProvider
import com.petplace.be.config.token.UserAuthentication
import com.petplace.be.user.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.springframework.security.core.Authentication
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
internal class UserTest @Autowired constructor(
    val userRepository: UserRepository
){
    @Test
    fun 사용자등록(){
        //given
        var user = User()
        user.nickname = "테스트"
        user.email = "test@email.com"

        //when
        var resultUser = userRepository.save(user)
        var result = userRepository.findById(resultUser.id!!)

        //then
        assertThat(resultUser.id, `is`(result.get().id))
        assertThat(user.nickname, `is`(result.get().nickname))
        assertThat(user.email, `is`(result.get().email))
        println(">>>>>>"+result.get().id)
    }

    @Test
    fun 토큰생성(){
        //given
        var user = User()
        user.nickname = "테스트"
        user.email = "test@email.com"


        var userResult = userRepository.save(user)

        val authentication: Authentication = UserAuthentication(
            userResult.id.toString(),
            null,
            null)
        var accessToken: String = JwtTokenProvider.generateToken(authentication)

        println("accessToken ::"+accessToken)
    }

}