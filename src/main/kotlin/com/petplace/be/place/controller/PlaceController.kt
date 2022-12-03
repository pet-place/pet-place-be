package com.petplace.be.place.controller

import com.petplace.be.place.PlaceSaveParam
import com.petplace.be.place.PlaceSaveResult
import com.petplace.be.place.service.PlaceService
import com.petplace.be.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/places")
class PlaceController (
    private val placeService: PlaceService
        ){

    @PostMapping
    fun createPlace(@RequestBody param: PlaceSaveParam): BaseResponse<PlaceSaveResult> {

        val response = placeService.savePlace(param);

        return BaseResponse(response);
    }
}