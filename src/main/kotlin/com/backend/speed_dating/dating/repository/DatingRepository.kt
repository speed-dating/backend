package com.backend.speed_dating.dating.repository

import com.backend.speed_dating.dating.entity.Dating
import org.springframework.data.jpa.repository.JpaRepository

interface DatingRepository : JpaRepository<Dating, Long> {}