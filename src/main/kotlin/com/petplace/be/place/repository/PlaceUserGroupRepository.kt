package com.petplace.be.place.repository

import com.petplace.be.place.domain.PlaceUserGroup
import com.petplace.be.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlaceUserGroupRepository: JpaRepository<PlaceUserGroup, Long> {

    fun findAllByUser(@Param("user") user:User): List<PlaceUserGroup>

    fun findAllByPlace_Id(@Param("placeId") placeId: Long): List<PlaceUserGroup>
}