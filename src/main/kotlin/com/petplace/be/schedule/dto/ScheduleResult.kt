package com.petplace.be.schedule.dto

data class ScheduleResult(
    val placeId: Long,
    val scheduleId: Long,
    val title: String,
    val contents: String,
    val creatorId: Long,
    val creatorNickname: String,
    val modifierId: Long,
    val modifierNickname: String,
)
