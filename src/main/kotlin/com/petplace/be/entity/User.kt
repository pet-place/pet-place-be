package com.petplace.be.entity

import com.petplace.be.user.enum.UserRole
import lombok.Getter
import org.hibernate.annotations.GenericGenerator
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

    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @Column(name = "user_role")
    var userRole: UserRole? = null
) {
    fun updateToken(accessToken: String?, refreshToken: String?) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}
