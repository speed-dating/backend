package com.backend.speed_dating.user.dto

import com.backend.speed_dating.common.annotation.ValidEnum
import com.backend.speed_dating.common.status.CountryEnum
import com.backend.speed_dating.common.status.Gender
import com.backend.speed_dating.user.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class UserCreationDto(
    @field:NotBlank
    @field:ValidEnum(enumClass = Gender::class, message = "It must be one of value [MAN, WOMAN]")
    @JsonProperty("gender")
    private val _gender: String?,

    @field:NotBlank
    @JsonProperty("phoneNumber")
    private val _phoneNumber: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = CountryEnum::class, message = "It must be one of value [KR]")
    @JsonProperty("country")
    private val _country: String?

){
    val phoneNumber: String
        get() = _phoneNumber!!

    val gender: Gender
        get() = Gender.valueOf(_gender!!)

    val country: CountryEnum
        get() = CountryEnum.valueOf(_country!!)
    
    private fun String.toLocaleDate() : LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity() : Member = Member(
        phoneNumber = phoneNumber,
        country = country,
        gender = gender,
    )
}