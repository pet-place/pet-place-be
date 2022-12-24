package com.petplace.be.pet.dto.param

import com.petplace.be.pet.Gender
import org.springframework.web.multipart.MultipartFile

data class PetSaveParam(
    val name: String,
    val age: String?,
    val gender: Gender,
    val characteristic: String?,
    val liked: String?,
    val disliked: String?,
    var placeId: Long,
    var profileImage: MultipartFile?
)