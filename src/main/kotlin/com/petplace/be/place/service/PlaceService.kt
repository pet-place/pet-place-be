package com.petplace.be.place.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.place.domain.Place
import com.petplace.be.place.dto.param.PlaceSaveParam
import com.petplace.be.place.dto.param.PlaceUpdateParam
import com.petplace.be.place.dto.result.PlaceResult
import com.petplace.be.place.dto.result.PlaceSaveResult
import com.petplace.be.place.dto.result.PlaceUpdateResult
import com.petplace.be.place.repository.PlaceRepository
import com.petplace.be.utils.FileUploader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
    val fileUploader: FileUploader
) {

    /* 플레이스 생성  */
    @Transactional
    fun savePlace(param: PlaceSaveParam): PlaceSaveResult {
        val place = placeRepository.save(
            Place(
            name = param.name,
            description = param.description,
            )
        )

        val uploadedUrl = validateAndUploadProfileUrl(param.profileImage, place.id!!)

        place.updateProfileImage(uploadedUrl)

        return PlaceSaveResult.generateFrom(place)
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
        val uploadedUrl = validateAndUploadProfileUrl(param.profileImage, param.id)

        var place = findPlaceById(param.id)
        place.update(param.name, param.description, uploadedUrl)

        return PlaceUpdateResult.generateFrom(place)
    }

    /* 플레이스 삭제 */
    @Transactional
    fun deletePlace(id: Long){
        var place = findPlaceById(id);
        place.delete()
    }

    private fun findPlaceById(id: Long): Place {
        return placeRepository.findById(id).orElseThrow {throw CommonException(ErrorCode.PLACE_NOT_FOUND) }
    }

    private fun uploadProfileImage(profileImage: MultipartFile, placeId: Long): String{
        val key = "$placeId/place-profile"
        return fileUploader.upload(profileImage, key)
    }

    private fun validateAndUploadProfileUrl(profileImage: MultipartFile?, placeId: Long): String{
        return if (profileImage == null){
            ""
        }else{
            val key = "$placeId/place-profile"
            fileUploader.upload(profileImage, key)
        }
    }

}
