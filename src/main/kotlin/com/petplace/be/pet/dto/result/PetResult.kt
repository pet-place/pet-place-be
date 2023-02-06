package com.petplace.be.pet.dto.result

import com.petplace.be.common.entity.BaseDto
import com.petplace.be.pet.Gender
import com.petplace.be.pet.domain.Pet
import com.petplace.be.pet.domain.Todo

data class PetResult(
    var id: Long,
    val name: String,
    val age: String?,
    val gender: Gender,
    val characteristic: String?,
    val liked: String?,
    val disliked: String?,
    val placeId: Long,
    val profileImageUrl: String?,
    val todoList: List<Todo>?
){
    constructor(pet: Pet): this(
        id = pet.id!! ,
        name = pet.name,
        age = pet.age,
        gender = pet.gender,
        characteristic = pet.characteristic,
        liked = pet.liked,
        disliked = pet.liked,
        placeId = pet.placeId,
        profileImageUrl = BaseDto.getProfileUrl(pet.profileImage),
        todoList = pet.todoList
    )
}
