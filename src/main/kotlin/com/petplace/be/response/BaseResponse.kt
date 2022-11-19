package com.petplace.be.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse<T>(
    val data: T? = null
): ErrorResponse() {
    override var isSuccess: Boolean = true
}