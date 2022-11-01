package com.petplace.be.config.jwt

import com.petplace.be.constract.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint: AuthenticationEntryPoint {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        log.error("Responding with unauthorized error. Message - {}", authException?.message ?: null)

        val unAuthorizationCode: ErrorCode = request!!.getAttribute("unauthorization.code") as ErrorCode

        request!!.setAttribute("response.failure.code", unAuthorizationCode.name)
        response!!.sendError(HttpServletResponse.SC_UNAUTHORIZED, unAuthorizationCode.message)
    }


}