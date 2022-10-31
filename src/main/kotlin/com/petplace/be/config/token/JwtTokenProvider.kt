package com.petplace.be.config.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import java.util.*

object JwtTokenProvider {
    const val JWT_SECRET: String = "ALSDFJAWIEJDSLFJSDKFAEOSPWOREIQEIRTOERGJKFBMXMVXLDKFSEORIWORTJLDFASDMCXVMZXKLVJKEJIAJDKFKJSDFJKASDKJFHEI"
    const val JWT_EXPIRATION_MS: Int = 604800000

    // generate token
    fun generateToken(authentication: Authentication):String{
        var now = Date()
        var expiryDate = Date(now.getTime() + JWT_EXPIRATION_MS)

        return Jwts.builder()
            .setSubject(authentication.principal as String) // 사용자
            .setIssuedAt(Date()) // 현재 시간 기반으로 생성
            .setExpiration(expiryDate) // 만료 시간 세팅
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
            .compact()
    }

    // get User Id
    // TODO deprecated 확인하기
    fun getUserIdFormJwt(token: String): String? {
        var claims: Claims = Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .getBody();

        return claims.subject
    }

    // jwt 유효성 검사
    fun validateToken(token: String?): Boolean{
       // ..
        return true
    }
}