package com.petplace.be.constract

enum class ErrorCode(val code: String, val message: String) {
    UNKNOWN("UNKNOWN", "알수 없는 에러"),

    DUPLICATED_NICKNAME("DUPLICATED_NICKNAME", "중복된 닉네임 입니다."),
    NOT_FOUND_USER("NOT_FOUND_USER", "사용자를 찾을 수 없습니다."),

    INVALID_ID_TOKEN("INVALID_ID_TOKEN", "잘못된 아이디 토큰입니다."),
    ACCESS_TOKEN_EXPIRED("JWT_TOKEN_EXPIRED", "Access Token 이 만료되었습니다."),
    NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "토큰 값이 없습니다.")
}