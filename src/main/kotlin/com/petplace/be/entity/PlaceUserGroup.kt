package com.petplace.be.entity

import com.petplace.be.common.entity.BaseTimeEntity
import com.petplace.be.user.domain.User
import javax.persistence.*

@Entity
class PlaceUserGroup(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "users_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place
): BaseTimeEntity()
