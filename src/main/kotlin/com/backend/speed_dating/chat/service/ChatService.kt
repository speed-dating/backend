package com.backend.speed_dating.chat.service

import com.backend.speed_dating.chat.dto.payload.ChatRoomCreationPayload
import com.backend.speed_dating.user.repository.MemberRepository
import com.backend.speed_dating.user.service.UserService
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class ChatService(
private val memberRepository: MemberRepository,
){
    fun createChatRoom(payload : ChatRoomCreationPayload){

        val members = memberRepository.findByIdIn(payload.userIds.map { it.toLong() })

        if (members.size != payload.userIds.size) {
            throw IllegalArgumentException("One or more users do not exist.")
        }


        // 유저 엔티티 필드 중 전화번호를 이용해 해쉬값 생성
        val hashedUserIds = members.map { member -> hashPhoneNumber(member.phoneNumber) }

        // agora api 를 호출하여 유저 생성
        hashedUserIds.forEach {
            hashedUserId->
        }
    }

    private fun hashPhoneNumber(phoneNumber: String) : String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashed = messageDigest.digest(phoneNumber.toByteArray()) // 000fff
        return hashed.joinToString { "%02x".format(it) }
    }



    fun deleteChatRoom(){}

    fun createAgoraToken(){}
}