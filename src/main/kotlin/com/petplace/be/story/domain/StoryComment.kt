package com.petplace.be.story.domain

import com.petplace.be.common.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "pp_story_comment")
class StoryComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "story_id")
    val storyId: Long? = 0L,

    @Column(name = "contents")
    var contents: String? = null,

    @Column(name = "created_by")
    val createdBy: Long,

    @Column(name = "modified_by")
    var modifiedBy: Long,
) : BaseEntity()
