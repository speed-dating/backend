package com.backend.speed_dating.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class SmsVerificationPayload(
    @field:NotBlank
    @field:Pattern(
        regexp = "\\+\\d{1,3}",
        message = "Invalid country code. It should start with a '+' and contain 1 to 3 digits.",
    )
    @JsonProperty("phoneNumber")
    private val _phoneNumber : String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "\\d{10,15}",
        message = "Invalid phone number. It should contain 10 to 15 digits.",
    )
    @JsonProperty("countryCode")
    private val _countryCode : String?,

){
    val phoneNumber : String
        get() = _phoneNumber!!

    val countryCode : String
        get() = _countryCode!!
}