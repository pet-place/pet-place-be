package com.petplace.be.user

import com.petplace.be.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository :JpaRepository<User, Long>{
    override fun <S : User?> save(entity: S): S {
        TODO("Not yet implemented")
    }

    override fun <S : User?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<User> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<User> {
        TODO("Not yet implemented")
    }
}