package com.backend.speed_dating.common.exception


class InvalidVerificationCodeException(
    val fieldName: String,
    override val message: String
) : IllegalArgumentException(message)