package com.petplace.be.response

open class ErrorResponse (
    val code: String?,
    val message: String?
) {
    constructor(): this(null, null)

    open var isSuccess: Boolean = false
}