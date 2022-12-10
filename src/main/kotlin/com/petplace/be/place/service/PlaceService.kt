package com.petplace.be.place.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.entity.Place
import com.petplace.be.place.dto.param.PlaceSaveParam
import com.petplace.be.place.dto.param.PlaceUpdateParam
import com.petplace.be.place.dto.result.PlaceResult
import com.petplace.be.place.dto.result.PlaceSaveResult
import com.petplace.be.place.dto.result.PlaceUpdateResult
import com.petplace.be.place.repository.PlaceRepository
import com.petplace.be.utils.S3Client
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
    private val s3Client: S3Client
) {

    /* 플레이스 생성*/
    fun savePlace(param: PlaceSaveParam): PlaceSaveResult {
        var uploadedUrl: String = ""
        if (param.profileImage != null) {
            uploadedUrl = s3Client.upload(param.profileImage!!)
        }

        val place = Place(
            name = param.name,
            description = param.description,
            profileUrl = uploadedUrl
        )
        val placeId = placeRepository.save(place).id

        return PlaceSaveResult(
            id = placeId!!
        )
    }

    /* 플레이스 조회*/
    fun findById(id: Long): PlaceResult {
        val place = findPlaceById(id)

        return PlaceResult(
            id = place.id!!,
            name = place.name,
            description = place.description,
            profileUrl = place.profileUrl,
            pets = place.pets
        )
    }

    /* 플레이스 수정 */
    @Transactional
    fun updatePlace(param: PlaceUpdateParam): PlaceUpdateResult{
        var place = findPlaceById(param.id)
        place.update(param.name, param.description, param.description)

        return PlaceUpdateResult.generateFrom(place)
    }

    /* 플레이스 삭제 */
    @Transactional
    fun deletePlace(id: Long){
        var place = findPlaceById(id);
        place.delete()
    }

    private fun findPlaceById(id: Long): Place{
        return placeRepository.findById(id).orElseThrow {throw CommonException(ErrorCode.PLACE_NOT_FOUND) }
    }


}
