package com.backend.speed_dating.user.service

import com.backend.speed_dating.common.authority.JwtTokenProvider
import com.backend.speed_dating.common.authority.TokenInfo
import com.backend.speed_dating.common.exception.InvalidInputException
import com.backend.speed_dating.common.status.Role
import com.backend.speed_dating.user.dto.LoginDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.backend.speed_dating.user.dto.UserCreationDto
import com.backend.speed_dating.user.entity.Member
import com.backend.speed_dating.user.entity.UserRole
import com.backend.speed_dating.user.model.response.GalleryResponseModel
import com.backend.speed_dating.user.model.response.ProfileResponseModel
import com.backend.speed_dating.user.model.response.TagResponseModel
import com.backend.speed_dating.user.repository.GalleryRepository
import com.backend.speed_dating.user.repository.MemberRepository
import com.backend.speed_dating.user.repository.TagRepository
import com.backend.speed_dating.user.repository.UserRoleRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder

@Transactional
@Service
class UserService(
    private val userRepository: MemberRepository,
    private val userRoleRepository: UserRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val tagRepository: TagRepository,
    private val galleryRepository: GalleryRepository,
){
    fun signup(payload: UserCreationDto) : String{
        val existUser = userRepository.findByPhoneNumber(payload.phoneNumber)
        if(existUser!=null) {
            throw  InvalidInputException("email","duplicated email")
        }

        val newUser : Member = payload.toEntity()
        userRepository.save(newUser)

        val userRole : UserRole = UserRole(id = null, role = Role.MEMBER, newUser)
        userRoleRepository.save(userRole)

        return "signup success"
    }

    // todo : remove
//    fun signIn(payload : LoginDto) : TokenInfo {
//        val authenticationToken = UsernamePasswordAuthenticationToken(payload.email,payload.password)
//        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
//
//        val userDetails = userService.findByEmail(payload.email)  // Adjust this method as needed
//
//        val userToken = UserToken(
//            id = userDetails.id.toString(),
//            nickname = userDetails.nickname,
//            avatarUrl = userDetails.avatarUrl,
//            phone = userDetails.phone,
//            gender = userDetails.gender,  // Assuming gender is an enum in your userDetails
//            birth = userDetails.birth.toString()
//        )
//
//        return jwtTokenProvider.createToken(userToken)
//    }

    @Transactional(readOnly = true)
    fun getProfile(userId: Long) : ProfileResponseModel {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("User not found with ID: $userId")
        }

        val tags = tagRepository.findByMemberId(userId)
        val galleries = galleryRepository.findByMemberId(userId)

        return ProfileResponseModel(
            id = user.id!!,
            nickname = user.nickname,
            birthDate = user.birthDate,
            profileImageUrl = user.profileImageUrl,
            introduce = user.introduce,
            phoneNumber = user.phoneNumber,
            gender = user.gender,
            tags = user.tags?.map { TagResponseModel(it.id!!, it.content) } ?: emptyList(),
            galleries = user.gallery?.map { GalleryResponseModel(it.id!!, it.imageUrl) } ?: emptyList(),
        )
    }

    fun getUserId() : Long {
        val user = userRepository.findAll()
        if(user.size > 0){
            return user[0].id!!
        }
        return 0
    }

    fun getUsersByIds(ids: List<Int>){

    }
}