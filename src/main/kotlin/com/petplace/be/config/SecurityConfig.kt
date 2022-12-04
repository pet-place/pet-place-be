package com.petplace.be.config

import com.petplace.be.config.jwt.JwtAuthenticationFilter
import com.petplace.be.config.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val tokenProvider: JwtTokenProvider
) {
    @Bean
    fun ignoringCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().antMatchers("/h2-console/**", "/hello") }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        http.authorizeRequests()
//            .antMatchers("/login/**").permitAll()
//            .and()
//            .formLogin().disable()
//            .httpBasic().disable()
//            .csrf().disable()
//
//        // Spring Security에서 session을 생성하거나 사용하지 않도록 설정
//        http.sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//        // JWT filter 적용
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
//
//        return http.build()
        return http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/login/**", "/user/sign-up/**").permitAll()
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .build()
    }

    // spring security role default prefix 'ROLE_'을 없애는 설정
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")

    /*
     * 토큰 필터 설정
     * */
    private fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(tokenProvider)
    }
}

