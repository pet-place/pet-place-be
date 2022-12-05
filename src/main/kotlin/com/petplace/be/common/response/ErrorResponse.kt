package com.petplace.be.common.response

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException

open class ErrorResponse (
    val code: String?,
    val message: String?
) {
    companion object {
        fun fromException(ex: Exception): ErrorResponse {
            if (ex is CommonException) {
                return ErrorResponse(
                    code = ex.errorCode.code,
                    message = ex.errorCode.message
                )
            }
            return ErrorResponse(
                code = ErrorCode.UNKNOWN.code,
                message = ErrorCode.UNKNOWN.message
            )
        }
    }

    constructor(): this(null, null)

    open var isSuccess: Boolean = false

    fun toJsonString(): String {
        return "{\"code\": \"$code\", \"message\": \"$message\", \"isSuccess\": \"$isSuccess\"}"
    }
}
