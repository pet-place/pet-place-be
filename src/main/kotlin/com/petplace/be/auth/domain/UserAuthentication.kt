package com.petplace.be.auth.domain

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class UserAuthentication(principal: Long?, credentials: String?, authorities: List<GrantedAuthority?>?) :
    UsernamePasswordAuthenticationToken(principal, credentials, authorities)
