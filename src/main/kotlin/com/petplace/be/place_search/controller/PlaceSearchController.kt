package com.petplace.be.place_search.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.place_search.dto.PlaceSearchResult
import com.petplace.be.place_search.service.PlaceSearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/places/search")
class PlaceSearchController(
    private val placeSearchService: PlaceSearchService,
) {
    @GetMapping
    fun searchByKeywords(@RequestParam keywords: String): BaseResponse<List<PlaceSearchResult>> {
        return BaseResponse(placeSearchService.searchByKeywords(keywords))
    }
}
