package com.petplace.be.config.jwt

import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.security.core.Authentication
import java.security.SignatureException
import java.util.*

@Configuration
@PropertySource("classpath:application-dev.properties")
class JwtTokenProvider {
    private val logger: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    @Value("\${jwt.config.secret-key}")
    lateinit var SECRET: String

    @Value("\${jwt.config.expiration}")
    lateinit var EXPIRATION: String


    // generate token
    fun generateToken(authentication: Authentication):String{
        var now = Date()
        var expiryDate = Date(now.time + EXPIRATION.toIntOrNull()!!)

        return Jwts.builder()
            .setSubject(authentication.principal as String) // 사용자
            .setIssuedAt(Date()) // 현재 시간 기반으로 생성
            .setExpiration(expiryDate) // 만료 시간 세팅
            .signWith(SignatureAlgorithm.HS512, SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
            .compact()
    }

    // get User Id
    // TODO deprecated 확인하기
    fun getUserIdFormJwt(token: String): String? {
        var claims: Claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();

        return claims.subject
    }

    // jwt 유효성 검사
    fun validateToken(token: String?): Boolean{
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }
}