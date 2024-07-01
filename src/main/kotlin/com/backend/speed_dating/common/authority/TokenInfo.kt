package com.backend.speed_dating.common.authority

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
)