package com.petplace.be.place.dto.result

import com.petplace.be.place.domain.Place
import com.petplace.be.user.domain.User

data class PlaceUserGroupResult(
    val id: Long,
    val user: User,
    val place: Place,
    val role: Boolean
)
