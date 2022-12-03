package com.petplace.be.pet.service

import com.petplace.be.constract.ErrorCode
import com.petplace.be.entity.Pet
import com.petplace.be.exception.CommonException
import com.petplace.be.pet.PetSaveParam
import com.petplace.be.pet.repository.PetRepository
import com.petplace.be.place.repository.PlaceRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val petRepository: PetRepository,
    private val placeRepository: PlaceRepository
) {

    fun savePet(param: PetSaveParam){
        //place 조회
        val savedPlace = placeRepository.findById(param.placeId)
            .orElseThrow {throw CommonException(ErrorCode.NOT_FOUND_PLACE)}

        //pet 저장
        val pet = Pet(
            name = param.name,
            age = param.age,
            gender = param.gender,
            characteristic = param.characteristic,
            liked = param.liked,
            disliked = param.disliked,
            place = savedPlace
        )

        petRepository.save(pet)

    }
}