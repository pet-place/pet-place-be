package com.petplace.be.pet.dto.result

import com.petplace.be.pet.TodoCategory
import com.petplace.be.pet.domain.Todo
import java.time.LocalDate

data class TodoResult(
    val id: Long,
    val category: TodoCategory,
    val frequency: Int,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val memo: String?
){
    companion object {
        fun generateFrom(todo: Todo): TodoResult {
            return TodoResult(
                id = todo.id!! ,
                category = todo.category,
                frequency = todo.frequency,
                startDate = todo.startDate,
                endDate = todo.endDate,
                memo = todo.memo
            )
        }
    }
}
