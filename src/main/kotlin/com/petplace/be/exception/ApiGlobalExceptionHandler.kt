package com.petplace.be.exception

import com.petplace.be.common.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class ApiGlobalExceptionHandler {
    @ExceptionHandler
    fun exceptionHandler(request: HttpServletRequest, response: HttpServletResponse, ex: Exception): ErrorResponse {
        ex.printStackTrace()
        return ErrorResponse.fromException(ex)
    }
}
