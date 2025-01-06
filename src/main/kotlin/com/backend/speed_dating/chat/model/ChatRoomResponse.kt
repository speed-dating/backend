package com.backend.speed_dating.chat.model

import java.time.LocalDateTime

data class ChatRoomListResponse(
    val chatRooms: List<ChatRoomSummary>,
)

data class ChatRoomSummary(
    val chatRoomId: Long,
    val opponentNickname: String,
    val opponentProfileImage: String?,
    val datingInfo: DatingSummary,
    val lastMessage: String,
    val lastMessageTimestamp: LocalDateTime
)

data class DatingSummary(
    val datingId: Long,  // 데이팅 ID
    val datingTitle: String  // 데이팅 제목
)