package com.petplace.be.pet.repository

import com.petplace.be.pet.domain.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TodoRepository :JpaRepository<Todo, Long>{

    fun findAllByPetId(@Param("petId") petId: Long) : MutableList<Todo>
    fun findByIdAndPetId(@Param("petId") petId: Long, @Param("todoId") todoId: Long) : Optional<Todo>
}