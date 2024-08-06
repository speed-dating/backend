package com.backend.speed_dating.dating.dto.payload

import com.backend.speed_dating.dating.entity.Dating
import com.backend.speed_dating.user.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DatingCreationPayload(
    @field:NotBlank
    @JsonProperty("title")
    private val _title: String?,

    @field:NotBlank
    @JsonProperty("description")
    private val _description: String?,

    @JsonProperty("imageUrl")
    private val _imageUrl: String?,

    @field:NotNull
    @field:Positive(message = "Price must be a positive value")
    @JsonProperty("price")
    private val _price: Double?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("startDate")
    private val _startDate: String?,

    @field:NotNull
    @JsonProperty("ownerId")
    private val _ownerId: Long?,

    @field:NotNull
    @JsonProperty("maleCapacity")
    private val _maleCapacity: Int?,

    @field:NotNull
    @JsonProperty("femaleCapacity")
    private val _femaleCapacity: Int?,
) {
    val title: String
        get() = _title!!

    val description: String
        get() = _description!!

    val imageUrl: String?
        get() = _imageUrl

    val price: Double
        get() = _price!!

    val startDate: LocalDate
        get() = _startDate!!.toLocaleDate()

    val maleCapacity : Int
        get() = _maleCapacity!!

    val femaleCapacity : Int
        get() = _femaleCapacity!!

    val ownerId: Long
        get() = _ownerId!!

    private fun String.toLocaleDate(): LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity(owner: Member): Dating = Dating(
        title = title,
        description = description,
        imageUrl = imageUrl,
        price = price,
        startDate = startDate,
        owner = owner,
        maleCapacity = maleCapacity,
        femaleCapacity = femaleCapacity,
    )
}