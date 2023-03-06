package com.petplace.be.place_search.service

import com.petplace.be.place_search.dto.PlaceSearchResult
import com.petplace.be.place_search.repository.PlaceSearchMapper
import org.springframework.stereotype.Service

@Service
class PlaceSearchService(
    private val placeSearchMapper: PlaceSearchMapper,
) {
    fun searchByKeywords(keywords: String): List<PlaceSearchResult> {
        return placeSearchMapper.findAllByKeywords(extractKeywords(keywords))
    }

    private fun extractKeywords(keywords: String): List<String> {
        return keywords.split(",")
            .flatMap { commaSplit -> commaSplit.split(" ") }
            .filter { keyword -> keyword.isNotEmpty() && keyword.isNotBlank() }
    }
}
