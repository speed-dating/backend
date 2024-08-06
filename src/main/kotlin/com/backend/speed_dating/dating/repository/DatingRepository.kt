package com.backend.speed_dating.dating.repository

import com.backend.speed_dating.dating.entity.Dating
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DatingRepository : JpaRepository<Dating, Long> {

    @Query("SELECT d FROM Dating d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.participants p LEFT JOIN FETCH p.member")
    fun findAllWithParticipants(): List<Dating>
}