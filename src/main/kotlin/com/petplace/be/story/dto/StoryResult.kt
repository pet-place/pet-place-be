package com.petplace.be.story.dto

data class StoryResult(
    val id: Long,
    val title: String,
    val contents: String,
    val photos: List<StoryPhotoResult>,
    val isLikedStory: Boolean
)
