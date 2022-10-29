package com.petplace.be.exception.handler;

import com.petplace.be.exception.base.BaseException;
import com.petplace.be.dto.ResponseCode;

public class InvalidIdTokenException extends BaseException {

    public InvalidIdTokenException(){
        this.code = ResponseCode.INVALID_ID_TOKEN.getCode();
        this.message = ResponseCode.INVALID_ID_TOKEN.getMessage();
        this.status = ResponseCode.INVALID_ID_TOKEN.getStatus();
    }
}
