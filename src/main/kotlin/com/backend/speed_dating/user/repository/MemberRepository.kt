package com.backend.speed_dating.user.repository

import com.backend.speed_dating.user.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import com.backend.speed_dating.user.entity.UserRole
import org.springframework.data.jpa.repository.EntityGraph

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByPhoneNumber(phoneNumber: String) : Member?

    @EntityGraph(attributePaths = ["tags", "gallery"])
    fun findWithTagsAndGalleryById(id: Long): Member?

    fun findByIdIn(ids: List<Long>) : List<Member>
}

interface UserRoleRepository : JpaRepository<UserRole, Long> {}