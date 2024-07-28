package com.backend.speed_dating.user.repository

import com.backend.speed_dating.user.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import com.backend.speed_dating.user.entity.UserRole

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByPhoneNumber(phoneNumber: String) : Member?
}

interface UserRoleRepository : JpaRepository<UserRole, Long> {}