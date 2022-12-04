package com.petplace.be.entity

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        @Id
        @GenericGenerator(name = "uuid", strategy ="com.petplace.be.utils.UuidGenerator")
        @GeneratedValue(generator = "uuid")
        var id: String? = null,

        val providerId: String? = null,
        var nickname: String,
        val email: String,
        val profileUrl: String? = null,
        val accessToken: String? = null,
        val phoneNumber: String? = null,

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
        var placeUserGroups: MutableList<PlaceUserGroup> = mutableListOf()
)