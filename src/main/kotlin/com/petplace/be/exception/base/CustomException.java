package com.petplace.be.exception.base;


import org.springframework.http.HttpStatus;

public interface CustomException {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}