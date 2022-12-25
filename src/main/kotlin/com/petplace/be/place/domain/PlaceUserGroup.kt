package com.petplace.be.place.domain

import com.petplace.be.common.entity.BaseEntity
import com.petplace.be.user.domain.User
import javax.persistence.*

@Entity
class PlaceUserGroup(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "pp_users_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place
): BaseEntity()
