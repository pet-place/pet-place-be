package com.petplace.be.schedule.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.schedule.domain.Schedule
import com.petplace.be.schedule.dto.ScheduleResult
import com.petplace.be.schedule.repository.ScheduleRepository
import com.petplace.be.user.domain.User
import com.petplace.be.user.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
@Transactional
class ScheduleService(
    val userService: UserService,
    val scheduleRepository: ScheduleRepository,
) {
    companion object {
        val targetDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("uuuu.MM.dd")
    }

    // TODO :: 플레이스 존재 여부 확인

    fun saveSchedule(placeId: Long, targetDateString: String, title: String, contents: String): Long {
        val createdBy = getCurrentUser()

        val savedSchedule = scheduleRepository.save(
            Schedule(
                targetDate = convertTargetDateStringToLocalDate(targetDateString),
                title = title,
                contents = contents,
                placeId = placeId,
                createdBy = createdBy,
                modifiedBy = createdBy
            )
        )
        return savedSchedule.id!!

    }

    private fun getCurrentUser(): User {
        return userService.getUserById(SecurityContextHolder.getContext().authentication.principal as Long)
    }

    private fun convertTargetDateStringToLocalDate(targetDateString: String): LocalDate {
        try {
            return LocalDate.parse(targetDateString, targetDateTimeFormatter)
        } catch (e: DateTimeParseException) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
    }

    fun updateSchedule(placeId: Long, scheduleId: Long, targetDateString: String, title: String, contents: String) {
        val modifiedBy = getCurrentUser()

        val schedule = findSchedule(scheduleId)
        schedule.targetDate = convertTargetDateStringToLocalDate(targetDateString)
        schedule.title = title
        schedule.contents = contents
        schedule.modifiedBy = modifiedBy
    }

    private fun findSchedule(scheduleId: Long): Schedule {
        return scheduleRepository.findById(scheduleId).orElseThrow { throw CommonException(ErrorCode.UNKNOWN) }
    }

    fun deleteSchedule(placeId: Long, scheduleId: Long) {
        val schedule = findSchedule(scheduleId)
        scheduleRepository.delete(schedule)
    }

    @Transactional(readOnly = true)
    fun getSchedule(placeId: Long, scheduleId: Long): ScheduleResult {
        val schedule = findSchedule(scheduleId)
        return convertToScheduleResult(schedule)
    }

    fun convertToScheduleResult(schedule: Schedule): ScheduleResult {
        return ScheduleResult(
            placeId = schedule.placeId,
            scheduleId = schedule.id!!,
            title = schedule.title!!,
            contents = schedule.contents!!,
            creatorId = schedule.createdBy.id!!,
            creatorNickname = schedule.createdBy.nickname!!,
            modifierId = schedule.modifiedBy.id!!,
            modifierNickname = schedule.modifiedBy.nickname!!
        )
    }

    @Transactional(readOnly = true)
    fun getSchedules(placeId: Long, page: Int, size: Int): List<ScheduleResult> {
        val pageable = PageRequest.of(page, size)
        val schedules = scheduleRepository.findAllByPlaceIdOrderByIdDesc(placeId, pageable)
        return schedules.map { schedule -> convertToScheduleResult(schedule) }
    }
}
