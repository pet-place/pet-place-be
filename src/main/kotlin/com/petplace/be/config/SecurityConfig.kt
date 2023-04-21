package com.petplace.be.config

import com.petplace.be.auth.filter.JwtAuthenticationFilter
import com.petplace.be.auth.filter.JwtExceptionHandlerFilter
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
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtExceptionHandlerFilter: JwtExceptionHandlerFilter
) {
    @Bean
    fun ignoringCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer {
                web: WebSecurity -> web.ignoring().antMatchers("/h2-console/**", "/api-docs/**", "/swagger-ui/**") }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        return http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/auth/**", "/users/sign-up/**").permitAll()
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .build()
    }

    // spring security role default prefix 'ROLE_'을 없애는 설정
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")

}
