package com.petplace.be.pet.dto.param

import com.petplace.be.pet.TodoCategory
import java.time.LocalDate

data class TodoSaveParam(
    val category: TodoCategory,
    val frequency: Int,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val memo: String?
)
