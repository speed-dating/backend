package com.backend.speed_dating.user.entity

import jakarta.persistence.*

@Entity
class Gallery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val imageUrl: String,

    @Column(nullable = false)
    val hidden: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = ForeignKey(name = "fk_gallery_member_id"))
    val member: Member,
)