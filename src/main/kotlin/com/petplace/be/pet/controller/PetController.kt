package com.petplace.be.pet.controller

import com.petplace.be.pet.PetSaveParam
import com.petplace.be.pet.service.PetService
import com.petplace.be.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Pet", description = "pet API")
@RestController
@RequestMapping("/pets")
class PetController(
    private val petService: PetService
) {

    @Operation(summary = "반려동물 등록", description = "플레이스에 반려동물이 등록됩니다.")
    @PostMapping
    fun savePet(@RequestBody param: PetSaveParam): BaseResponse<Void>{
        petService.savePet(param)
        return BaseResponse()
    }
}