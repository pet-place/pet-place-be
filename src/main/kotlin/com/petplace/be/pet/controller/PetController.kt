package com.petplace.be.pet.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.pet.dto.param.PetSaveParam
import com.petplace.be.pet.dto.param.PetUpdateParam
import com.petplace.be.pet.dto.result.PetResult
import com.petplace.be.pet.service.PetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Pet", description = "pet API")
@RestController
@RequestMapping("/pets")
class PetController(
    private val petService: PetService
) {
    @Operation(summary = "반려동물 등록", description = "플레이스에 반려동물이 등록됩니다.")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun savePet(param: PetSaveParam): BaseResponse<PetResult> {
        val response = petService.savePet(param)
        return BaseResponse(response)
    }

    @Operation(summary = "반려동물 수정", description = "반려동물을 수정합니다.")
    @PutMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePet(param: PetUpdateParam): BaseResponse<PetResult> {
        val response = petService.updatePet(param)
        return BaseResponse(response)
    }

    @Operation(summary = "반려동물 상세조회", description = "반려동물을 조회합니다.")
    @GetMapping("/{id}")
    fun findPet(@PathVariable id: Long): BaseResponse<PetResult> {
        val response = petService.findByPetId(id)
        return BaseResponse(response)
    }

    @Operation(summary = "반려동물 삭제", description = "반려동물을 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deletePet(@PathVariable id: Long): BaseResponse<PetResult> {
        petService.deleteById(id)
        return BaseResponse()
    }
}
