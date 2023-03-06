package com.petplace.be.place.dto.result

import com.petplace.be.place.PlaceRole
import com.petplace.be.place.domain.Place

data class PlaceByUserResult(
    val id: Long,
    val name: String,
    val profileUrl: String?,
    val role:PlaceRole
){
    constructor(place: Place, role: PlaceRole) : this(
        id = place.id!!,
        name = place.name,
        profileUrl = place.profileUrl,
        role = role
    )
}
