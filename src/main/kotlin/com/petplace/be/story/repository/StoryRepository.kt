package com.petplace.be.story.repository

import com.petplace.be.story.domain.Story
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface StoryRepository: JpaRepository<Story, Long> {
    fun findAllByOrderByIdDesc(pageable: Pageable): List<Story>
}
