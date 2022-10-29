package com.petplace.be.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    OK("DONE", "성공", HttpStatus.OK),
    INVALID_ID_TOKEN("INVALID_ID_TOKEN","잘못된 아이디 토큰입니다", HttpStatus.BAD_REQUEST);

    private final String code;         // 응답 코드
    private final String message;      // 응답 메세지
    private final HttpStatus status;   // 응답 상태코드
}
