package com.petplace.be.pet.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.pet.domain.Pet
import com.petplace.be.pet.dto.param.PetSaveParam
import com.petplace.be.pet.dto.param.PetUpdateParam
import com.petplace.be.pet.dto.result.PetResult
import com.petplace.be.pet.repository.PetRepository
import com.petplace.be.place.repository.PlaceRepository
import com.petplace.be.utils.FileUploader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PetService(
    private val petRepository: PetRepository,
    private val placeRepository: PlaceRepository,
    private val fileUploader: FileUploader
) {
    @Transactional
    fun savePet(param: PetSaveParam): PetResult {
        val savedPlace = placeRepository.findById(param.placeId)
            .orElseThrow {throw CommonException(ErrorCode.PLACE_NOT_FOUND) }

        val pet = petRepository.save(
            Pet(
            name = param.name,
            age = param.age,
            gender = param.gender,
            characteristic = param.characteristic,
            liked = param.liked,
            disliked = param.disliked,
            place = savedPlace,
            profileImage = null
            )
        )

        val uploadedUrl = validateAndUploadProfileUrl(param.profileImage, pet.place.id!!, pet.id!!)

        pet.updateProfileImage(uploadedUrl);

        return PetResult.generateFrom(pet)
    }

    @Transactional
    fun updatePet(param: PetUpdateParam): PetResult{
        val pet = findPet(param.id)

        pet.updateProfileImage(validateAndUploadProfileUrl(param.profileImage, param.id, param.placeId))
        pet.update(param)

        return PetResult.generateFrom(pet)
    }

    fun findByPetId(id: Long): PetResult{
        return PetResult.generateFrom(findPet(id))
    }

    fun findPet(id: Long): Pet{
        return petRepository.findById(id).orElseThrow { throw CommonException(ErrorCode.PET_NOT_FOUND) }
    }

    @Transactional
    fun deleteById(id: Long){
        petRepository.deleteById(id)
    }

    private fun validateAndUploadProfileUrl(profileImage: MultipartFile?, placeId: Long, petId: Long): String{
        return if (profileImage == null){
            ""
        }else{
            var fileName = "pet-profile-$petId"
            val key = "place-$placeId/$fileName"
            return fileUploader.upload(profileImage, key)
        }
    }
}
