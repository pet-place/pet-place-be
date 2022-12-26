package com.petplace.be.place.dto.result

import com.petplace.be.place.PlaceRole
import com.petplace.be.place.domain.Place

data class PlaceByUserResult(
    val id: Long,
    val name: String,
    val profileUrl: String?,
    val role:PlaceRole
){
    companion object {
        fun generateFrom(place: Place, role: PlaceRole): PlaceByUserResult{
            return PlaceByUserResult(
                id = place.id!!,
                name = place.name,
                profileUrl = place.profileUrl,
                role = role
            )
        }
    }
}
