package com.petplace.be.config.token

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt:String? = getJwtFromRequest(request) //request에서 jwt 토큰을 꺼낸다.

        if (jwt != null && JwtTokenProvider.validateToken(jwt)) {
            val userId: String? = JwtTokenProvider.getUserIdFormJwt(jwt) //jwt에서 사용자 id를 꺼낸다.
            val authentication = UserAuthentication(userId, null, null) //id를 인증한다.
            authentication.setDetails(WebAuthenticationDetailsSource().buildDetails(request)) //기본적으로 제공한 details 세팅
            SecurityContextHolder.getContext()
                .setAuthentication(authentication) //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
        } else {
            if (jwt == null) {
                request.setAttribute("unauthorization", "401 인증키 없음.")
            }
            if (JwtTokenProvider.validateToken(jwt)) {
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