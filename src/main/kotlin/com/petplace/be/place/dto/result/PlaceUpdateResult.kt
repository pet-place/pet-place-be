package com.petplace.be.place.dto.result

import com.petplace.be.pet.dto.result.PetResult
import com.petplace.be.place.domain.Place

data class PlaceUpdateResult(
    var id: Long,
    var name: String,
    var description: String,
    val profileUrl: String?,
    var pets: List<PetResult>
){
    constructor(place: Place): this(
        id = place.id!! ,
        name = place.name,
        description = place.description,
        profileUrl = place.profileUrl,
        pets = place.pets.map {pet -> (PetResult(pet))}
            .toList()
    )
}
