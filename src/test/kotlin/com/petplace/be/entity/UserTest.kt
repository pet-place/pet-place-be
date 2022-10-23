package com.petplace.be.entity

import com.petplace.be.user.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ActiveProfiles("dev")
internal class UserTest @Autowired constructor(
    val userRepository: UserRepository
){
    @Test
    fun 사용자등록(){
        //given
        var user = User()
        user.id = 1L
        user.nickname = "테스트"
        user.email = "test@email.com"

        //when
        userRepository.save(user)
        var result = userRepository.findById(user.id!!)

        //then
        assertThat(user.id, `is`(result.get().id))
        assertThat(user.nickname, `is`(result.get().nickname))
        assertThat(user.email, `is`(result.get().email))
    }
}