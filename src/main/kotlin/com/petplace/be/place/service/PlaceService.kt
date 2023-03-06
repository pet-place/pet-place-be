package com.petplace.be.place.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.place.PlaceRole
import com.petplace.be.place.domain.Place
import com.petplace.be.place.domain.PlaceUserGroup
import com.petplace.be.place.dto.param.PlaceSaveParam
import com.petplace.be.place.dto.param.PlaceUpdateParam
import com.petplace.be.place.dto.result.*
import com.petplace.be.place.repository.PlaceRepository
import com.petplace.be.place.repository.PlaceUserGroupRepository
import com.petplace.be.user.service.UserService
import com.petplace.be.utils.FileUploader
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
    private val placeUserGroupRepository: PlaceUserGroupRepository,
    private val userService: UserService,
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

        if (uploadedUrl != null) {
            place.updateProfileImage(uploadedUrl)
        }

        // PlaceUserGroup 생성
        val userId = SecurityContextHolder.getContext().authentication.principal
        val user = userService.getUserById(userId as Long)

        val placeUserGroup = PlaceUserGroup(
            user = user,
            place = place,
            role = PlaceRole.OWNER
        )
        placeUserGroupRepository.save(placeUserGroup)

        return PlaceSaveResult(place)
    }

    /* 플레이스 조회*/
    fun findById(id: Long): PlaceResult {
        val place = findPlaceById(id)

        return PlaceResult(place)
    }

    /* 플레이스 목록 조회*/
    fun findAllByUser(): List<PlaceByUserResult> {
        val userId = SecurityContextHolder.getContext().authentication.principal
        val user = userService.getUserById(userId as Long)

        val resultList = placeUserGroupRepository.findAllByUser(user)
        return resultList.stream()
            .map { p -> PlaceByUserResult(p.place, p.role!!) }
            .toList()
    }

    /* 플레이스 수정 */
    @Transactional
    fun updatePlace(param: PlaceUpdateParam): PlaceUpdateResult{
        val place = findPlaceById(param.id)

        val uploadedUrl: String? = validateAndUploadProfileUrl(param.profileImage, param.id)
        place.update(param.name, param.description, uploadedUrl)

        return PlaceUpdateResult(place)
    }

    /* 플레이스 삭제 */
    @Transactional
    fun deletePlace(id: Long){
        var place = findPlaceById(id);
        var group = placeUserGroupRepository.findAllByPlace_Id(id)

        group.forEach { group -> group.delete() }
        place.delete()
    }

    /* 플레이스 멤버 목록 조회 */
    fun findPlaceMember(id: Long):List<PlaceMemberResult>{
        var placeUserGroup = placeUserGroupRepository.findAllByPlace_Id(id)

        return placeUserGroup.map {
                placeUserGroup ->
                val user = placeUserGroup.user
                PlaceMemberResult(user,placeUserGroup.role!!)
        }.toList()
    }

    /* 플레이스 멤버 삭제 */
    @Transactional
    fun deleteUserFromPlace(id: Long, userId: Long){
        var group = placeUserGroupRepository.findByPlace_Id_AndUserId(id, userId)

        if (group.role == PlaceRole.OWNER) {
            throw CommonException(ErrorCode.UNKNOWN)
        }

        group.delete();
    }

    private fun findPlaceById(id: Long): Place {
        return placeRepository.findById(id).orElseThrow {throw CommonException(ErrorCode.PLACE_NOT_FOUND) }
    }

    private fun validateAndUploadProfileUrl(profileImage: MultipartFile?, placeId: Long): String?{
        if (profileImage != null && !profileImage.isEmpty){
            val key = "place-$placeId/place-profile"
            return fileUploader.upload(profileImage, key)
        }
        return null
    }
}
