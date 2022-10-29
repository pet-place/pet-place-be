package com.petplace.be.exception;

import com.petplace.be.dto.BaseResponseDto;
import com.petplace.be.exception.base.CustomException;
import com.petplace.be.exception.handler.InvalidIdTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
        InvalidIdTokenException.class
    })
    private ResponseEntity<BaseResponseDto> handlerCustomException(CustomException e){
        return new ResponseEntity<>(
                new BaseResponseDto(e.getCode(), e.getMessage(), e.getStatus())
                , HttpStatus.OK
        );
    }
}
