package com.petplace.be.entity

import com.petplace.be.auth.domain.JwtTokenProvider
import com.petplace.be.auth.domain.UserAuthentication
import com.petplace.be.user.domain.User
import com.petplace.be.user.repository.UserRepository
import com.petplace.be.user.service.UserService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
internal class UserTest @Autowired constructor(
    val userRepository: UserRepository,
    val userService: UserService,
    val jwtTokenProvider: JwtTokenProvider
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
            userResult.id,
            null,
            null)
        var accessToken: String = jwtTokenProvider.generateAccessToken(authentication)

        println("accessToken ::"+accessToken)
    }

//    @Test
//    fun 닉네임_중복테스트(){
//        //given
//        var user = User()
//        user.nickname = "ssyoni"
//        var result = userRepository.save(user)
//
//        var user2 = User()
//        user2.nickname = "ssyoni"
//        var result2 = userRepository.save(user2)
//
//        //when
//        var param = UserUpdateParam(id = result2.id!!, nickName = result.nickname ?: "")
//
//        //then
//        assertThrows(IllegalStateException::class.java) { userService.updateUser(param)}
//        assertThatThrownBy(){ userService.updateUser(param)}.hasMessage(ErrorCode.DUPLICATED_NICKNAME.message)
//    }
}
