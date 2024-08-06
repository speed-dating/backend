package com.backend.speed_dating.user.repository

import com.backend.speed_dating.user.entity.Gallery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GalleryRepository : JpaRepository<Gallery, Long> {
    fun findByMemberId(memberId: Long): List<Gallery>
}