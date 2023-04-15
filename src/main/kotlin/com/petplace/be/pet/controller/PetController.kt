package com.petplace.be.pet.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.pet.TodoCategory
import com.petplace.be.pet.dto.param.PetSaveParam
import com.petplace.be.pet.dto.param.PetUpdateParam
import com.petplace.be.pet.dto.result.PetResult
import com.petplace.be.pet.service.PetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "Pet", description = "pet API")
@RestController
@RequestMapping("/pets")
class PetController(
    private val petService: PetService
) {
    @Operation(summary = "반려동물 등록", description = "플레이스에 반려동물이 등록됩니다.")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun savePet(@ModelAttribute param: PetSaveParam): BaseResponse<PetResult> {
        val response = petService.savePet(param)
        return BaseResponse(response)
    }

    @Operation(summary = "반려동물 수정", description = "반려동물을 수정합니다.")
    @PutMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updatePet(@ModelAttribute param: PetUpdateParam): BaseResponse<PetResult> {
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

    @Operation(summary = "반려동물 todo 카테고리 목록 조회", description = "Todo 등록시 사용할 카테고리 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "한글 문자열로 반환됩니다.")
    @GetMapping("/todos/categories")
    fun getCategoryList(): BaseResponse<List<String>> {
        val array = TodoCategory.values()
        val categoryList = mutableListOf<String>()
        for (a in array){
            categoryList.add(a.value)
        }
        return BaseResponse(categoryList)
    }
}