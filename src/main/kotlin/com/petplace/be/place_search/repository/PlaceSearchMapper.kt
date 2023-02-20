package com.petplace.be.place_search.repository

import com.petplace.be.place_search.dto.PlaceSearchResult
import org.apache.ibatis.annotations.Mapper

@Mapper
interface PlaceSearchMapper {
    fun findAllByKeywords(keywords: List<String>): List<PlaceSearchResult>
}
