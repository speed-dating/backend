package com.backend.speed_dating.common.service

import com.backend.speed_dating.user.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import com.backend.speed_dating.user.entity.User as EntityUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        userRepository.findByEmail(email = username!!)
            ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException("Not Found User")

    private fun createUserDetails(user : EntityUser) : UserDetails =
        User(
            user.email,
            passwordEncoder.encode(user.password),
            user.userRole!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
}