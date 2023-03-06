package com.petplace.be.place.dto.result

import com.petplace.be.place.PlaceRole
import com.petplace.be.user.domain.User

data class PlaceMemberResult(
    val userId: Long,
    val profileUrl: String?,
    val nickname: String,
    val role: PlaceRole
){
    constructor(user: User, role: PlaceRole): this(
        userId = user.id!!,
        profileUrl = user.profileUrl,
        nickname = user.nickname!!,
        role = role
    )
}
