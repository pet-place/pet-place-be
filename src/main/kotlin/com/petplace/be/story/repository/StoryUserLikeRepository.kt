package com.petplace.be.story.repository

import com.petplace.be.story.domain.StoryUserLike
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StoryUserLikeRepository : JpaRepository<StoryUserLike, Long> {
    fun deleteByStoryId(storyId: Long)
    fun existsByStoryIdAndUserId(storyId: Long, userId: Long): Boolean
    fun findByStoryIdAndUserId(storyId: Long, userId: Long): Optional<StoryUserLike>
    fun countAllByStoryId(storyId: Long): Int
}
