package com.petplace.be.pet

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonValue

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class TodoCategory(
    @JsonValue
    val korValue: String
    ) {
    MEAL("밥"), SNACK("간식"), WALK("산책"), MEDICINE("약")
}