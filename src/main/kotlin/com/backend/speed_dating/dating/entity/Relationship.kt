package com.backend.speed_dating.dating.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime


@Entity
class Relationship(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val opponentId: Long,

    @Column(nullable = false)
    val datingId: Long,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
){}