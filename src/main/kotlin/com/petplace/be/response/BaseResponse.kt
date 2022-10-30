package com.petplace.be.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse<T>(
    val code: String? = null,
    val message: String? = null,
    val data: T? = null,
    val isSuccess: Boolean = true
)