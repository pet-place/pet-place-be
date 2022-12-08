package com.petplace.be.common.enums

enum class ErrorCode(val code: String, val message: String) {
    UNKNOWN("UNKNOWN", "알수 없는 에러"),

    DUPLICATE_NICKNAME("DUPLICATE_NICKNAME", "중복된 닉네임 입니다."),
    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),

    INVALID_GOOGLE_ID_TOKEN("INVALID_GOOGLE_ID_TOKEN", "잘못된 구글 아이디 토큰입니다."),

    NO_ACCESS_TOKEN("NO_ACCESS_TOKEN", "액세스 토큰 값이 없습니다."),

    INVALID_JWT_SIGNATURE("INVALID_JWT_SIGNATURE", "JWT 시그니처가 유효하지 않습니다."),
    MALFORMED_JWT("MALFORMED_JWT", "JWT 형식이 올바르지 않습니다."),
    EXPIRED_JWT("EXPIRED_JWT", "만료된 JWT입니다."),
    UNSUPPORTED_JWT("UNSUPPORTED_JWT", "지원되지 않는 형식의 JWT입니다."),
    NO_JTW_CLAIM("NO_JTW_CLAIM", "존재하지 않는 JWT claim을 조회하였습니다."),

    PLACE_NOT_FOUND("PLACE_NOT_FOUND", "플레이스가 존재하지 않습니다."),
}