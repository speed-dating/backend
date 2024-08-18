package com.backend.speed_dating.chat.repository

import com.backend.speed_dating.chat.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository  : JpaRepository<ChatRoom, Long>