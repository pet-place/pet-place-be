package com.petplace.be.story.domain

import com.petplace.be.common.entity.BaseEntity
import javax.persistence.*

@Entity
class StoryComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "story_id")
    val storyId: Long? = 0L,

    @Column(name = "contents")
    var contents: String? = null,
) : BaseEntity()
