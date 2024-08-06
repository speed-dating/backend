package com.backend.speed_dating.user.repository

import com.backend.speed_dating.user.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Long>{
    fun findByMemberId(memberId: Long): List<Tag>
}