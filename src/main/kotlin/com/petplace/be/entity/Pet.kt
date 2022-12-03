package com.petplace.be.entity

import com.petplace.be.entity.base.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Pet(
    @Id
    @GeneratedValue
    val id: Long? = null,

    val name: String,               // 이름
    val age: String,                // 나이
    val gender: String,             // 성별
    var characteristic: String,     // 특징
    var liked: String,               // 좋아하는 것
    var disliked: String,            // 싫어하는 것

    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place
) : BaseEntity()