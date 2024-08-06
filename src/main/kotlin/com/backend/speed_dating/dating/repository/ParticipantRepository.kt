package com.backend.speed_dating.dating.repository

import com.backend.speed_dating.dating.entity.Participant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipantRepository : JpaRepository<Participant, Long>{}