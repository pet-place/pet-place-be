package com.petplace.be.pet

data class PetSaveParam(
    val name: String,
    val age: String,
    val gender: Gender,
    val characteristic: String,
    val liked: String,
    val disliked: String,
    var placeId: Long
)
