package com.petplace.be.schedule.dto

data class SaveAndUpdateScheduleParam(
    val targetDate: String,
    val title: String,
    val contents: String,
)
