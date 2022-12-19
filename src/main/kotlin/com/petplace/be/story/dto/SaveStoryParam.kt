package com.petplace.be.story.dto

import org.springframework.web.multipart.MultipartFile

data class SaveStoryParam(
    val title: String,
    val contents: String,
    val files: List<MultipartFile> = mutableListOf(),
)
