package com.petplace.be.schedule.repository

import com.petplace.be.schedule.domain.Schedule
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun findAllByPlaceIdOrderByIdDesc(placeId: Long, pageable: Pageable): List<Schedule>
}
