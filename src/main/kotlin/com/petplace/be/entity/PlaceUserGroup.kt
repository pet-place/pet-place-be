package com.petplace.be.entity

import com.petplace.be.entity.base.BaseTimeEntity
import javax.persistence.*

@Entity
class PlaceUserGroup(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "users_id")
    var user:User,

    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place
): BaseTimeEntity()