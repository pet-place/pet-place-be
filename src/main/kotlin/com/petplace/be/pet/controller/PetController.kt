package com.petplace.be.pet.controller

import com.petplace.be.pet.PetSaveParam
import com.petplace.be.pet.service.PetService
import com.petplace.be.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pets")
class PetController(
    private val petService: PetService
) {

    @PostMapping
    fun savePet(@RequestBody param: PetSaveParam): BaseResponse<Void>{
        petService.savePet(param)
        return BaseResponse()
    }
}