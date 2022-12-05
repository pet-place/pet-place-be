package com.petplace.be.auth.domain

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${jwt.config.secret-key}")
    lateinit var SECRET: String

    @Value("\${jwt.config.access.expiration}")
    lateinit var ACCESS_EXPIRATION: String

    @Value("\${jwt.config.refresh.expiration}")
    lateinit var REFRESH_EXPIRATION: String

    val KEY: Key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)) }

    // generate access token
    fun generateAccessToken(authentication: Authentication): String {
        val now = LocalDateTime.now()
        val expiryDate = now.plusSeconds(ACCESS_EXPIRATION.toLong())

        return Jwts.builder()
            .setSubject(authentication.principal.toString()) // 사용자
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // 현재 시간 기반으로 생성
            .setExpiration(Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant())) // 만료 시간 세팅
            .signWith(KEY) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
            .compact()
    }

    fun generateRefreshToken(): String {
        val now = LocalDateTime.now()
        val expiryDate = now.plusSeconds(REFRESH_EXPIRATION.toLong())

        return Jwts.builder()
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // 현재 시간 기반으로 생성
            .setExpiration(Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant())) // 만료 시간 세팅
            .signWith(KEY) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
            .compact()
    }

    // get User Id
    fun getUserIdFromToken(token: String): String? {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).body.subject
    }

    // jwt 유효성 검사
    fun validateToken(token: String?) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).body.subject
        } catch (e: SignatureException) {
            throw CommonException(ErrorCode.INVALID_JWT_SIGNATURE)
        } catch (e: MalformedJwtException) {
            throw CommonException(ErrorCode.MALFORMED_JWT)
        } catch (e: ExpiredJwtException) {
            throw CommonException(ErrorCode.EXPIRED_JWT)
        } catch (e: UnsupportedJwtException) {
            throw CommonException(ErrorCode.UNSUPPORTED_JWT)
        } catch (e: IllegalArgumentException) {
            throw CommonException(ErrorCode.NO_JTW_CLAIM)
        }
    }
}
