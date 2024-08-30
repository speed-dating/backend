package com.backend.speed_dating.auth.model.response

import com.backend.speed_dating.common.authority.TokenInfo
import com.backend.speed_dating.user.dto.UserResponseModel

data class UserVerificationResponse(
    val token : TokenInfo?,
    val user: UserResponseModel?
){}