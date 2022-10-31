package com.petplace.be.entity

import org.hibernate.annotations.GenericGenerator
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
        @GenericGenerator(name = "uuid", strategy ="com.petplace.be.utils.UuidGenerator")
        @GeneratedValue(generator = "uuid")
        var id: String? = null
}