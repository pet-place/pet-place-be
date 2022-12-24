package com.petplace.be.place.dto.result

import com.petplace.be.pet.domain.Pet
import com.petplace.be.place.domain.Place

data class PlaceResult(
    val id: Long,
    val name: String,
    val description: String,
    val profileUrl: String?,
    val pets: MutableList<Pet>
){
    companion object {
        fun generateFrom(place: Place): PlaceResult{
            return PlaceResult(
                id = place.id!!,
                name = place.name,
                description = place.description,
                profileUrl = place.profileUrl,
                pets = place.pets
            )
        }
    }
}
