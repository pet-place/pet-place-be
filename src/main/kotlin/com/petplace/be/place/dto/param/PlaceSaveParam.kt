package com.petplace.be.place.dto.param

import org.springframework.web.multipart.MultipartFile

data class PlaceSaveParam(
    var name:String,
    var description:String,
    var profileImage: MultipartFile? =null,
)
