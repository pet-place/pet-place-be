package com.petplace.be.place.domain

import com.petplace.be.common.entity.BaseEntity
import com.petplace.be.place.PlaceRole
import com.petplace.be.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "pp_place_user_group")
class PlaceUserGroup(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Enumerated(value = EnumType.STRING)
    var role: PlaceRole? = PlaceRole.MEMBER,

    @ManyToOne
    @JoinColumn(name = "pp_users_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place
): BaseEntity()
