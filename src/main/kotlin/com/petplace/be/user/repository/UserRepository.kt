package com.petplace.be.user.repository

import com.petplace.be.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }

    fun findByEmail(email: String): Optional<User> {
        TODO("Not yet implemented")
    }
}