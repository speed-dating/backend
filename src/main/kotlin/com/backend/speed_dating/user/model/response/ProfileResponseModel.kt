package com.backend.speed_dating.user.model.response

import com.backend.speed_dating.common.status.Gender
import java.time.LocalDate

data class ProfileResponseModel(
    val id : Long,
    val gender: Gender,
    val phoneNumber: String,
    val nickname: String,
    val introduce: String,
    val birthDate : LocalDate,
    val profileImageUrl : String,
    val tags: List<TagResponseModel>,
    val galleries: List<GalleryResponseModel>,
){}

data class TagResponseModel(
    val id: Long,
    val content: String,
){}

data class GalleryResponseModel(
    val id: Long,
    val imageUrl : String,
){}