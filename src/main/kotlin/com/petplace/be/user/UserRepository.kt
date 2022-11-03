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

    //@Query("select u from User u where u.nickname = :nickName and u.id <> :id")
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u where u.nickname = :nickName and u.id <> :id")
    fun existsByNickname(@Param("nickName") nickName: String,
                        @Param("id") id: String): Boolean

    fun findByEmail(email: String): Optional<User>
}
