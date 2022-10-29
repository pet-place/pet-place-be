package com.petplace.be.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException implements CustomException {

    protected String code;          // 응답코드
    protected String message;       // 응답메세지
    protected Object data;
    protected HttpStatus status;    // 응답코드
}