package com.backend.speed_dating.user.dto

import com.backend.speed_dating.common.annotation.ValidEnum
import com.backend.speed_dating.common.status.Gender
import com.backend.speed_dating.user.entity.User
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class UserCreationDto(
    @field:NotBlank
    @JsonProperty("email")
    private val _email : String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자 사이로 입력해주세요."
    )
    @JsonProperty("password")
    private val _password: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthDate")
    private val _birthDate: String?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Gender::class, message = "It must be one of value [MAN, WOMAN]")
    @JsonProperty("gender")
    private val _gender: String?,
){
    val email : String
        get() = _email!!

    val password: String
        get() = _password!!

    val birthDate: LocalDate
        get() = _birthDate!!.toLocaleDate()

    val name: String
        get() = _name!!

    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    
    private fun String.toLocaleDate() : LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity() : User = User(
        email= email,
        password = password,
        birthDate = birthDate,
        gender = gender,
        name = name,
    )
}