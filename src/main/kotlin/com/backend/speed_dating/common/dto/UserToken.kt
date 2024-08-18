package com.backend.speed_dating.common.dto

import com.backend.speed_dating.common.status.Gender

data class UserToken(
    val id: Long,
    val nickname: String,           // The user nickname (max 64 characters)
    val avatarUrl: String,         // The user avatar URL (max 256 characters)
    val phone: String,             // The user's phone number (max 32 characters)
    val gender: Gender,                // The user gender: 1 for Male, 2 for Female, 0 for Unknown
    val birth: String,
) {
}