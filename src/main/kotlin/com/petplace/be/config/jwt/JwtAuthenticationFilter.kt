package com.petplace.be.config.jwt

import org.springframework.beans.factory.annotation.Autowired
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
        val jwt:String? = getJwtFromRequest(request)

        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            val userId: String? = jwtTokenProvider.getUserIdFormJwt(jwt)
            val authentication = UserAuthentication(userId, null, null)
            authentication.setDetails(WebAuthenticationDetailsSource().buildDetails(request))
            SecurityContextHolder.getContext().setAuthentication(authentication)

        } else {
            if (jwt == null) {
                request.setAttribute("unauthorization", "401 인증키 없음.")
            }
            if (jwtTokenProvider.validateToken(jwt)) {
                request.setAttribute("unauthorization", "401-001 인증키 만료.")
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        var bearerToken: String = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring("Bearer ".length)
        }
        return null
    }
}