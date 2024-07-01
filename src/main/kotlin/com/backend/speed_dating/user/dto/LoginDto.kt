package com.backend.speed_dating.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class LoginDto(
    @field:NotBlank
    @JsonProperty("email")
    private val _email : String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password:String?,
){
    val email : String
        get() = _email!!

    val password: String
        get() = _password!!
}