package com.petplace.be.entity

import javax.persistence.*

@Entity
@Table(name = "user")
class User {
        lateinit var providerId: String
        lateinit var nickname: String
        lateinit var email: String
        lateinit var profileUrl: String
        lateinit var accessToken: String
        lateinit var phoneNumber: String
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null
}