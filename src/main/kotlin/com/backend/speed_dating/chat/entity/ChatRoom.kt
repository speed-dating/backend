package com.backend.speed_dating.chat.entity

import com.backend.speed_dating.user.entity.Member
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_chat_user_contstraint", columnNames = ["member_id", "opponent_id","dating_id"])]
)
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = true)
    val lastMessage : String? = null,

    @Column(nullable = false)
    val agoraUuid: String,

    @Column(nullable = false)
    val datingId: Long,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    var deletedAt: LocalDateTime? = null,

    @Column(nullable = true)
    var opponentLeftAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="opponent_id", insertable = false, updatable = false)
    val opponent: Member,
){

}