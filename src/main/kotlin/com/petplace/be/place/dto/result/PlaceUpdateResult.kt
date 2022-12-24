package com.petplace.be.place.dto.result

import com.petplace.be.pet.domain.Pet
import com.petplace.be.place.domain.Place

data class PlaceUpdateResult(
    var id: Long,
    var name: String,
    var description: String,
    val profileUrl: String?,
    var pets: MutableList<Pet>
){
    companion object {
        fun generateFrom(place: Place): PlaceUpdateResult{
            return PlaceUpdateResult(
                id = place.id!! ,
                name = place.name,
                description = place.description,
                profileUrl = place.profileUrl,
                pets = place.pets
            )
        }
    }
}
