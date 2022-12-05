package com.petplace.be.place.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.place.PlaceResult
import com.petplace.be.place.PlaceSaveParam
import com.petplace.be.place.PlaceSaveResult
import com.petplace.be.place.service.PlaceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Place", description = "place API")
@RestController
@RequestMapping("/places")
class PlaceController(
    private val placeService: PlaceService
) {
    @Operation(summary = "플레이스 등록", description = "플레이스가 등록됩니다.")
    @PostMapping
    fun createPlace(@RequestBody param: PlaceSaveParam): BaseResponse<PlaceSaveResult> {
        val response = placeService.savePlace(param);
        return BaseResponse(response);
    }

    @Operation(summary = "플레이스 조회", description = "플레이스를 조회합니다.")
    @GetMapping("/{id}")
    fun findPlace(@PathVariable id: Long): BaseResponse<PlaceResult> {
        val placeResult = placeService.findById(id)
        return BaseResponse(placeResult)
    }
}
