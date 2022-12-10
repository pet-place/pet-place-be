package com.petplace.be.place.dto.result

import com.petplace.be.entity.Pet
import com.petplace.be.entity.Place
import java.time.LocalDateTime

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
