package com.petplace.be.config.jwt

import com.petplace.be.constract.ErrorCode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization:String? = getJwtFromRequest(request)

        if (authorization != null && jwtTokenProvider.validateToken(authorization)) {

            val userId: String? = jwtTokenProvider.getUserIdFormJwt(authorization)

            val authentication = UserAuthentication(userId, null, null)

            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication

        } else {
            if (authorization == null) {
                request.setAttribute("unauthorization", ErrorCode.NOT_FOUND_TOKEN.message)
            }
            if (authorization != null && jwtTokenProvider.validateToken(authorization)) {
                request.setAttribute("unauthorization", ErrorCode.ACCESS_TOKEN_EXPIRED.message)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken: String = request.getHeader("Authorization") ?: return null
        if (bearerToken.startsWith("Bearer")){
            return bearerToken.substring("Bearer ".length)
        }
        return null
    }
}
