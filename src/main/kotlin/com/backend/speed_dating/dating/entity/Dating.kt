package com.backend.speed_dating.dating.entity

import jakarta.persistence.*
import com.backend.speed_dating.user.entity.Member
import java.time.LocalDate

@Entity
class Dating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = true)
    val imageUrl: String? = null,

    @Column(nullable = false)
    val price: Double,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val startDate: LocalDate,

    @Column(nullable = false)
    val maleCapacity : Int,

    @Column(nullable = false)
    val femaleCapacity : Int,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dating")
    val participants: List<Participant>? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", foreignKey = ForeignKey(name = "fk_dating_owner_id"), nullable = false)
    val owner: Member,
)

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_dating_user_constraint", columnNames = ["dating_id", "user_id"])]
)
class Participant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dating_id", foreignKey = ForeignKey(name = "fk_participant_dating_id"), nullable = false)
    val dating: Dating,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_participant_user_id"), nullable = false)
    val member: Member,
)