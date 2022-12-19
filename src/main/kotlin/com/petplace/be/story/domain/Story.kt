package com.petplace.be.story.domain

import com.petplace.be.common.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "pp_story")
class Story(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "contents")
    var contents: String? = null,

    @OneToMany(mappedBy = "storyId")
    var photos: MutableList<StoryPhoto> = mutableListOf(),

    @OneToMany(mappedBy = "storyId")
    var comments: MutableList<StoryComment> = mutableListOf(),

    @Column(name = "like_count")
    var likeCount: Int? = 0,
) : BaseEntity()
