package com.petplace.be.entity

import com.petplace.be.entity.base.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class UserPlaceGroup(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "users_id")
    var user:User,

    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place,

    override var createAt: LocalDateTime,
    override var updateAt: LocalDateTime
): BaseTimeEntity(createAt, updateAt)