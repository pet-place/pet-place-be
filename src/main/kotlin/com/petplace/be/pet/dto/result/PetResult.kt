package com.petplace.be.pet.dto.result

import com.petplace.be.entity.Pet
import com.petplace.be.pet.Gender

data class PetResult(
    var id: Long,
    val name: String,
    val age: String?,
    val gender: Gender,
    val characteristic: String?,
    val liked: String?,
    val disliked: String?,
    val placeId: Long,
    val profileImageUrl: String?
){
    companion object {
        fun generateFrom(pet: Pet): PetResult {
            return PetResult(
                id = pet.id!! ,
                name = pet.name,
                age = pet.age,
                gender = pet.gender,
                characteristic = pet.characteristic,
                liked = pet.liked,
                disliked = pet.liked,
                placeId = pet.place.id!!,
                profileImageUrl = pet.profileImage
            )
        }
    }
}
