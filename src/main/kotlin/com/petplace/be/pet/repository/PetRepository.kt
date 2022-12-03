package com.petplace.be.pet.repository

import com.petplace.be.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : JpaRepository<Pet, Long>{
}