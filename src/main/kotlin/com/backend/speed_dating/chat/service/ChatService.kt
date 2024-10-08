package com.backend.speed_dating.chat.service

import com.backend.speed_dating.chat.dto.payload.ChatRoomCreationPayload
import com.backend.speed_dating.chat.dto.payload.ChatRoomDeletionPayload
import com.backend.speed_dating.chat.entity.ChatRoom
import com.backend.speed_dating.chat.repository.ChatRoomRepository
import com.backend.speed_dating.common.status.Gender
import com.backend.speed_dating.user.repository.MemberRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val memberRepository: MemberRepository,
    private val agoraService: AgoraService,
    private val chatRoomRepository: ChatRoomRepository,
){
//    fun createChatRoom(payload : ChatRoomCreationPayload){
//
//        val members = memberRepository.findByIdIn(payload.userIds)
//
//        if (members.size != payload.userIds.size) {
//            throw IllegalArgumentException("One or more users do not exist.")
//        }
//
//        val female = members.find { it.gender == Gender.FEMALE }
//        val male = members.find { it.gender == Gender.MALE }
//
//        if(female == null || male == null){
//            throw IllegalArgumentException("Failed to find both a female and a male user.")
//        }
//
//        val femaleUuid = agoraService.getChatUserUuid(female.nickname)
//            ?: agoraService.registerChatUser(, female.nickname)
//            ?: throw IllegalArgumentException("failed to register female")
//        val maleUuid = agoraService.getChatUserUuid(male.nickname)
//            ?: agoraService.registerChatUser(male.nickname)
//            ?: throw IllegalArgumentException("failed to register male")
//
//        val femaleChatRoom = ChatRoom(
//            id = null,
//            agoraUuid = femaleUuid,
//            datingId = payload.datingId,
//            member = female,
//            opponent = male,
//        )
//        val maleChatRoom = ChatRoom(
//            id = null,
//            agoraUuid = maleUuid,
//            datingId = payload.datingId,
//            member = male,
//            opponent = female,
//        )
//
//        chatRoomRepository.saveAll(listOf( femaleChatRoom, maleChatRoom))
//
//        // todo: agora chatting 방 생성
//    }
//
//    fun deleteChatRoom(payload: ChatRoomDeletionPayload){
//        // 1. memberId, opponentId, datingId 에 해당하는 chatRoom 조회
//        val myChatRoom = chatRoomRepository.findByMemberIdAndOpponentIdAndDatingId(
//            memberId = payload.memberId,
//            opponentId = payload.opponentId,
//            datingId = payload.datingId,
//        )
//            ?: throw java.lang.IllegalArgumentException("no chat room found")
//        val opponentChatRoom = chatRoomRepository.findByMemberIdAndOpponentIdAndDatingId(
//            memberId = payload.opponentId,
//            opponentId = payload.memberId,
//            datingId = payload.datingId,
//            )
//            ?: throw java.lang.IllegalArgumentException("no chat room found")
//
//        myChatRoom.deletedAt = LocalDateTime.now()
//        opponentChatRoom.opponentLeftAt = LocalDateTime.now()
//        chatRoomRepository.saveAll(listOf(myChatRoom, opponentChatRoom))
//
//        // 4. agora에 채팅방 삭제되었다는 api 호출
//    }

}