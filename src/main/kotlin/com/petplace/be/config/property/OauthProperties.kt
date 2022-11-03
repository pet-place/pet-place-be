package com.petplace.be.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "oauth")
data class OauthProperties(
    val jwt : JwtProperties,
    val google : GoogleProperties
){
    data class JwtProperties(
        val secretKey: String,
        val expiration: Int
            )

    data class GoogleProperties(
        val clientId : String
    )
}
