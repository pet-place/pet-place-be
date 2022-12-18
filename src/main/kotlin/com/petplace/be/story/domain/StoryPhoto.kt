package com.petplace.be.story.domain

import com.petplace.be.common.entity.BaseEntity
import javax.persistence.*

@Entity
class StoryPhoto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "story_id")
    val storyId: Long? = 0L,

    @Column(name = "url")
    var url: String? = null,
): BaseEntity()
