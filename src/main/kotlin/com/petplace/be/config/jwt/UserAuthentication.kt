package com.petplace.be.config.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class UserAuthentication : UsernamePasswordAuthenticationToken {
    constructor(principal: Long?, credentials: String?) : super(principal, credentials) {}
    constructor(
        principal: String?, credentials: String?,
        authorities: List<GrantedAuthority?>?
    ) : super(principal, credentials, authorities) {
    }
}