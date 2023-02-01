package com.petplace.be.place.dto.result

import com.petplace.be.common.entity.BaseDto
import com.petplace.be.place.domain.Place

data class PlaceSaveResult(
    val id: Long,
    val name: String,
    val description: String,
    val profileUrl: String?
){
    constructor(place: Place): this(
        id = place.id!!,
        name = place.name,
        description = place.description,
        profileUrl = BaseDto.getProfileUrl(place.profileUrl)
    )
}
