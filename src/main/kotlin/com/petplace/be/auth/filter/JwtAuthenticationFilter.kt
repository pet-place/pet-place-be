package com.petplace.be.auth.filter

import com.petplace.be.auth.domain.JwtTokenProvider
import com.petplace.be.auth.domain.UserAuthentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken: String? = getAccessToken(request)

        // TODO :: 어뷰징으로 accessToken 없이 API 호출을 하는 경우에 대한 처리 필요
        if (null == accessToken) {
            filterChain.doFilter(request, response)
            return
        }

        tokenProvider.validateToken(accessToken)

        val userId: String? = tokenProvider.getUserIdFromToken(accessToken)

        val authentication = UserAuthentication(userId?.toLong(), null, null)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }

    private fun getAccessToken(request: HttpServletRequest): String? {
        val bearerToken: String = request.getHeader("Authorization") ?: return null
        if (bearerToken.startsWith("Bearer")) {
            return bearerToken.substring("Bearer ".length)
        }
        return null
    }
}
