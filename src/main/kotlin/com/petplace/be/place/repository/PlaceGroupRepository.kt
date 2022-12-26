package com.petplace.be.place.repository

import com.petplace.be.place.domain.PlaceUserGroup
import com.petplace.be.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlaceGroupRepository: JpaRepository<PlaceUserGroup, Long> {

    fun findAllByUser(@Param("user") user:User): MutableList<PlaceUserGroup>
}