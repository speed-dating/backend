package com.backend.speed_dating.dating.service

import com.backend.speed_dating.dating.repository.DatingRepository
import org.springframework.stereotype.Service

@Service
class DatingService(
    private val datingRepository: DatingRepository,
){

    fun findAllWithParticipants(){
        val result = datingRepository.findAllWithParticipants()
    }

}