package com.petplace.be.user.domain

import com.petplace.be.entity.PlaceUserGroup
import javax.persistence.*

@Entity
@Table(name = "pp_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,

    @Column(name = "provider_id")
    val providerId: String? = null,

    @Column(name = "nickname")
    var nickname: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "profile_url")
    val profileUrl: String? = null,

    @Column(name = "access_token")
    var accessToken: String? = null,

    @Column(name = "phone_number")
    val phoneNumber: String? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var placeUserGroups: MutableList<PlaceUserGroup> = mutableListOf()
)
