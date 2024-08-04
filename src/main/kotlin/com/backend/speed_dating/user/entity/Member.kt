package com.backend.speed_dating.user.entity

import com.backend.speed_dating.common.status.CountryEnum
import com.backend.speed_dating.common.status.Gender
import com.backend.speed_dating.common.status.Role
import com.backend.speed_dating.dating.entity.Dating
import com.backend.speed_dating.dating.entity.Participant
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_phone_number_constraint", columnNames = ["phoneNumber"])]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender : Gender,

    @Column(nullable = false)
    val phoneNumber : String,

    @Column(nullable = false)
    val nickname: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val country : CountryEnum,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = true)
    val profileImageUrl: String,
){
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val userRole : List<UserRole>? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val participants: List<Participant>? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    val organizedDatings: List<Dating>? = null // 주최한 데이팅 목록
}

@Entity

class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role : Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_user_id"))
    val member: Member,
)
