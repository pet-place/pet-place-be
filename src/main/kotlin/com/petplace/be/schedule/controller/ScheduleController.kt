package com.petplace.be.schedule.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.schedule.dto.SaveAndUpdateScheduleParam
import com.petplace.be.schedule.dto.ScheduleResult
import com.petplace.be.schedule.service.ScheduleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/places/{placeId}/schedule")
class ScheduleController(
    val scheduleService: ScheduleService,
) {
    @PostMapping
    fun saveSchedule(
        @PathVariable placeId: Long,
        @RequestBody saveAndUpdateScheduleParam: SaveAndUpdateScheduleParam
    ): BaseResponse<Long> {
        return BaseResponse(
            scheduleService.saveSchedule(
                placeId,
                saveAndUpdateScheduleParam.targetDate,
                saveAndUpdateScheduleParam.title,
                saveAndUpdateScheduleParam.contents
            )
        )
    }

    @PutMapping("/{scheduleId}")
    fun updateSchedule(
        @PathVariable placeId: Long,
        @PathVariable scheduleId: Long,
        @RequestBody saveAndUpdateScheduleParam: SaveAndUpdateScheduleParam
    ): BaseResponse<Void> {
        scheduleService.updateSchedule(
            placeId,
            scheduleId,
            saveAndUpdateScheduleParam.targetDate,
            saveAndUpdateScheduleParam.title,
            saveAndUpdateScheduleParam.contents
        )
        return BaseResponse()
    }

    @DeleteMapping("/{scheduleId}")
    fun deleteSchedule(@PathVariable placeId: Long, @PathVariable scheduleId: Long): BaseResponse<Void> {
        scheduleService.deleteSchedule(placeId, scheduleId)
        return BaseResponse()
    }


    @GetMapping("/{scheduleId}")
    fun getSchedule(@PathVariable placeId: Long, @PathVariable scheduleId: Long): BaseResponse<ScheduleResult> {
        return BaseResponse(scheduleService.getSchedule(placeId, scheduleId))
    }

    @GetMapping
    fun getSchedules(
        @PathVariable placeId: Long,
        @RequestParam page: Int,
        @RequestParam size: Int
    ): BaseResponse<List<ScheduleResult>> {
        return BaseResponse(scheduleService.getSchedules(placeId, page - 1, size))
    }
}
