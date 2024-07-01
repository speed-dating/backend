package com.backend.speed_dating.user.service

import com.backend.speed_dating.common.authority.JwtTokenProvider
import com.backend.speed_dating.common.authority.TokenInfo
import com.backend.speed_dating.common.exception.InvalidInputException
import com.backend.speed_dating.common.status.Role
import com.backend.speed_dating.user.dto.LoginDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.backend.speed_dating.user.dto.UserCreationDto
import com.backend.speed_dating.user.entity.User
import com.backend.speed_dating.user.entity.UserRole
import com.backend.speed_dating.user.repository.UserRepository
import com.backend.speed_dating.user.repository.UserRoleRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder

@Transactional
@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
){
    fun signup(payload: UserCreationDto) : String{
        val existUser = userRepository.findByEmail(payload.email)
        if(existUser!=null) {
            throw  InvalidInputException("email","duplicated email")
        }

        val newUser : User = payload.toEntity()
        userRepository.save(newUser)

        val userRole : UserRole = UserRole(id = null, role = Role.MEMBER, newUser)
        userRoleRepository.save(userRole)

        return "signup success"
    }

    fun signIn(payload : LoginDto) : TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(payload.email,payload.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }
}