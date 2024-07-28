package com.backend.speed_dating.common.service

import com.backend.speed_dating.user.entity.Member
import com.backend.speed_dating.user.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    private val userRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(phoneNumber: String?): UserDetails =
        userRepository.findByPhoneNumber(phoneNumber = phoneNumber!!)
            ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException("Not Found User")

    private fun createUserDetails(user : Member) : UserDetails =
        User(
            user.phoneNumber,
            passwordEncoder.encode(user.phoneNumber),
            user.userRole!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
}