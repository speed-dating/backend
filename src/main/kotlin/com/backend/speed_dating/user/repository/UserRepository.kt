package com.backend.speed_dating.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.backend.speed_dating.user.entity.User
import com.backend.speed_dating.user.entity.UserRole

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String) : User?
}

interface UserRoleRepository : JpaRepository<UserRole, Long> {}