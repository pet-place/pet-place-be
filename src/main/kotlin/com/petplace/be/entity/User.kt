package com.petplace.be.entity

import javax.persistence.*

@Entity
@Table(name = "user")
class User() {
    var providerId: String? = null
    var nickname: String? = null
    var email: String? = null
    var profileUrl: String? = null
    var token: String? = null
    var phone_number: String? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}