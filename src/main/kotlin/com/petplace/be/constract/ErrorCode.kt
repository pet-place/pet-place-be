package com.petplace.be.constract

enum class ErrorCode(val code: String, val message: String) {
    INVALID_ID_TOKEN("INVALID_ID_TOKEN", "잘못된 아이디 토큰입니다.")
}