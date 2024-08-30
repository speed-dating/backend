package com.backend.speed_dating.user.dto

import com.backend.speed_dating.common.status.Gender
import com.backend.speed_dating.user.entity.Member

data class UserResponseModel(
    val id: Number,
    val nickname: String,
    val gender: Gender,
    val profileImageUrl: String,
){
    companion object {
        fun fixture(
            id: Number = 1,
            nickname: String = "MockUser",
            gender: Gender = Gender.MALE,
            profileImageUrl: String = "http://example.com/profile.jpg"
        ): UserResponseModel {
            return UserResponseModel(
                id = id,
                nickname = nickname,
                gender = gender,
                profileImageUrl = profileImageUrl
            )
        }

        fun of(member : Member) : UserResponseModel{
            return UserResponseModel(
                id = member.id!!,
                nickname = member.nickname,
                gender = member.gender,
                profileImageUrl = member.profileImageUrl,
            )
        }
    }
}