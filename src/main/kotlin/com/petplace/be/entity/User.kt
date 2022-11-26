package com.petplace.be.entity

import lombok.Getter
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @GenericGenerator(name = "uuid", strategy ="com.petplace.be.utils.UuidGenerator")
        @GeneratedValue(generator = "uuid")
        val id: String? = null,

        val providerId: String? = null,
        var nickname: String,
        var email: String,
        val profileUrl: String? = null,
        var accessToken: String? = null,
        val phoneNumber: String? = null,
        var refreshToken: String? = null
){
        fun updateToken(accessToken: String?, refreshToken: String?){
                this.accessToken = accessToken
                this.refreshToken = refreshToken
        }
}