package com.petplace.be.story.repository

import com.petplace.be.story.domain.StoryPhoto
import org.springframework.data.jpa.repository.JpaRepository

interface StoryPhotoRepository: JpaRepository<StoryPhoto, Long> {
}
