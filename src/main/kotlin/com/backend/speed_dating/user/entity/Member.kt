package com.backend.speed_dating.user.entity

import com.backend.speed_dating.common.status.CountryEnum
import com.backend.speed_dating.common.status.Gender
import com.backend.speed_dating.common.status.Role
import jakarta.persistence.*

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
    @Enumerated(EnumType.STRING)
    val country : CountryEnum,
){
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val userRole : List<UserRole>? = null
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
