package com.petplace.be.story.repository

import com.petplace.be.story.domain.StoryComment
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface StoryCommentRepository : JpaRepository<StoryComment, Long> {
    fun findAllByOrderByIdDesc(pageable: Pageable): List<StoryComment>
}
