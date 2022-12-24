package com.petplace.be.place.dto.result

import com.petplace.be.place.domain.Place

data class PlaceSaveResult(
    val id: Long,
    val name: String,
    val description: String,
    val profileUrl: String?
){
    companion object {
        fun generateFrom(place: Place): PlaceSaveResult {
            return PlaceSaveResult(
                id = place.id!!,
                name = place.name,
                description = place.description,
                profileUrl = place.profileUrl
            )
        }
    }
}
