package com.petplace.be.place.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.place.dto.param.PlaceSaveParam
import com.petplace.be.place.dto.param.PlaceUpdateParam
import com.petplace.be.place.dto.param.PlaceUserDeleteParam
import com.petplace.be.place.dto.result.*
import com.petplace.be.place.service.PlaceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "Place", description = "place API")
@RestController
@RequestMapping("/places")
class PlaceController(
    private val placeService: PlaceService
) {
    @Operation(summary = "플레이스 등록", description = "플레이스가 등록됩니다.")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPlace(@ModelAttribute param: PlaceSaveParam): BaseResponse<PlaceSaveResult> {
        val response = placeService.savePlace(param)
        return BaseResponse(response);
    }

    @Operation(summary = "플레이스 조회", description = "플레이스를 조회합니다.")
    @GetMapping("/{id}")
    fun findPlace(@PathVariable id: Long): BaseResponse<PlaceResult> {
        val placeResult = placeService.findById(id)
        return BaseResponse(placeResult)
    }

    @Operation(summary = "플레이스 수정", description = "플레이스가 수정됩니다.")
    @PutMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updatePlace(@PathVariable id: Long, @ModelAttribute param: PlaceUpdateParam): BaseResponse<PlaceUpdateResult> {
        val response = placeService.updatePlace(id, param);
        return BaseResponse(response);
    }

    @Operation(summary = "플레이스 삭제", description = "플레이스를 삭제합니다.")
    @DeleteMapping("/{id}")
    fun findByIdAndUser(@PathVariable id: Long): BaseResponse<Void> {
        val response = placeService.deletePlace(id);
        return BaseResponse();
    }

    @Operation(summary = "플레이스 목록 조회", description = "사용자의 플레이스 목록을 조회합니다.")
    @GetMapping()
    fun findAllPlace(): BaseResponse<List<PlaceByUserResult>> {
        val response = placeService.findAllByUser()
        return BaseResponse(response)
    }

    @Operation(summary = "플레이스 멤버 목록 조회", description = "플레이스의 멤버 목록을 조회합니다.")
    @GetMapping("/{id}/members")
    fun findAllPlaceMember(@PathVariable id: Long): BaseResponse<List<PlaceMemberResult>> {
        val response = placeService.findPlaceMember(id)
        return BaseResponse(response)
    }

    @Operation(summary = "플레이스 멤버 삭제", description = "플레이스 그룹에 있는 멤버를 삭제합니다.")
    @DeleteMapping("/member")
    fun deleteUserFromPlace(@RequestBody param: PlaceUserDeleteParam): BaseResponse<List<PlaceMemberResult>> {
        placeService.deleteUserFromPlace(param.id, param.userId)
        return BaseResponse()
    }

}
