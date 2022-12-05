package com.petplace.be.user.dto

data class SignUpParam(
    val googleIdToken: String,
    val nickname: String
)
