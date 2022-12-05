package com.petplace.be.common.exception

import com.petplace.be.common.enums.ErrorCode

class CommonException : RuntimeException {
    val errorCode: ErrorCode

    constructor(errorCode: ErrorCode) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, message: String) : super(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, cause: Throwable) : super(cause) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, message: String, cause: Throwable) : super(message, cause) {
        this.errorCode = errorCode
    }
}
