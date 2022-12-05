package com.petplace.be.place.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.entity.Place
import com.petplace.be.place.PlaceResult
import com.petplace.be.place.PlaceSaveParam
import com.petplace.be.place.PlaceSaveResult
import com.petplace.be.place.repository.PlaceRepository
import org.springframework.stereotype.Service

@Service
class PlaceService(
    private val placeRepository: PlaceRepository
) {

    /* 플레이스 생성*/
    fun savePlace(param: PlaceSaveParam): PlaceSaveResult {
        val place = Place(
            name = param.name,
            description = param.description
        )
        val placeId = placeRepository.save(place).id

        return PlaceSaveResult(
            id = placeId!!
        )
    }

    /* 플레이스 조회*/
    fun findById(id: Long): PlaceResult {
        val place = placeRepository.findById(id)
            .orElseThrow {throw CommonException(ErrorCode.PLACE_NOT_FOUND) }

        return PlaceResult(
            id = place.id!!,
            name = place.name,
            description = place.description,
            pets = place.pets,
            createdAt = place.createAt(),
            updatedAt = place.updateAt()
        )
    }

}
