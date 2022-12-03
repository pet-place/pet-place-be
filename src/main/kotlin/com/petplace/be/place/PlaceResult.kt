package com.petplace.be.place

import com.petplace.be.entity.Pet

data class PlaceResult(
    val id: Long,
    val name: String,
    val description: String,
    //val profileUrl: String,
    val createdAt: Unit,
    val updatedAt: Unit,
    val pets: MutableList<Pet>
)
