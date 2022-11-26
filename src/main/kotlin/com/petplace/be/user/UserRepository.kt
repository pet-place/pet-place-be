package com.petplace.be.user

import com.petplace.be.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsByEmail(email: String): Boolean

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u where u.nickname = :nickName")
    fun existsByNickname(@Param("nickName") nickName: String): Boolean

    fun findByEmail(email: String): Optional<User>

    fun findByRefreshToken(refreshToken: String): Optional<User>
}
