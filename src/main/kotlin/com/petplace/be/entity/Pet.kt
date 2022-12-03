package com.petplace.be.entity

import com.petplace.be.entity.base.BaseEntity
import com.petplace.be.pet.Gender
import javax.persistence.*

@Entity
class Pet(
    @Id
    @GeneratedValue
    val id: Long? = null,

    val name: String,               // 이름
    val age: String,                // 나이

    @Enumerated(EnumType.STRING)
    val gender: Gender,             // 성별
    var characteristic: String,     // 특징
    var liked: String,               // 좋아하는 것
    var disliked: String,            // 싫어하는 것

    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place
) : BaseEntity()