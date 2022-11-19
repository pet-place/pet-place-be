package com.petplace.be.exception

import com.petplace.be.constract.ErrorCode
import com.petplace.be.response.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class ApiGlobalExceptionHandler {
    @ExceptionHandler
    fun exceptionHandler(request: HttpServletRequest, response: HttpServletResponse, ex: Exception): ErrorResponse {
        val errorCode = if(ex is CommonException) {
            ex.errorCode
        } else {
            ErrorCode.UNKNOWN
        }

        return ErrorResponse(
            code = errorCode.code,
            message = errorCode.message
        )
    }
}