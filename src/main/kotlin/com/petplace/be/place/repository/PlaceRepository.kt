package com.petplace.be.place.repository

import com.petplace.be.place.domain.Place
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository : JpaRepository<Place, Long>{
}