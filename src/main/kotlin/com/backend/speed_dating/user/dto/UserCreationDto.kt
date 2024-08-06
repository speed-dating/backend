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
    @JsonProperty("nickname")
    private val _nickname: String?,

    @field:NotBlank
    @JsonProperty("profileImage")
    private val _profileImage: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = CountryEnum::class, message = "It must be one of value [KR]")
    @JsonProperty("country")
    private val _country: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthDate")
    private val _birthDate: String?,

){
    val phoneNumber: String
        get() = _phoneNumber!!

    val gender: Gender
        get() = Gender.valueOf(_gender!!)

    val country: CountryEnum
        get() = CountryEnum.valueOf(_country!!)

    val birthDate: LocalDate
        get() = _birthDate!!.toLocaleDate()

    val nickname: String
        get() = _nickname!!

    val profileImage: String
        get() = _profileImage!!

    private fun String.toLocaleDate() : LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity() : Member = Member(
        phoneNumber = phoneNumber,
        country = country,
        gender = gender,
        birthDate = birthDate,
        nickname = nickname,
        profileImageUrl = profileImage,
        introduce = "",
    )
}