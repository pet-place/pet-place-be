package com.petplace.be.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseResponseDto<T> {

    private String code;        // 응답 코드
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;     // 응답 메세지
    private int status;         // 응답 HTTP 상태
    private T data;        // 응답 데이터

    @Builder
    public BaseResponseDto(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status.value();
    }

    @Builder
    public BaseResponseDto() {
        this.code = ResponseCode.OK.getCode();
        this.data = null;
        this.status = ResponseCode.OK.getStatus().value();
    }

    @Builder
    public BaseResponseDto(T obj) {
        this.code = ResponseCode.OK.getCode();
        this.data = obj;
        this.status = ResponseCode.OK.getStatus().value();
    }

}