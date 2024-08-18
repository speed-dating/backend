package com.backend.speed_dating.chat.dto.payload

data class ChatRoomCreationPayload(
    val userIds : List<Long>,
    val datingId: Long,
)

data class ChatRoomDeletionPayload(
    val memberId: Long,
    val opponentId: Long,
    val datingId: Long,
)