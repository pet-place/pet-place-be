package com.petplace.be.story.domain

import com.petplace.be.common.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "pp_story_user_like")
class StoryUserLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "story_id")
    val storyId: Long,

    @Column(name = "user_id")
    val userId: Long,
) : BaseEntity()
