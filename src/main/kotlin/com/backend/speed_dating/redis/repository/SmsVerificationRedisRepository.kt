package com.backend.speed_dating.redis.repository

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@RedisHash("SmsVerification", timeToLive = 180)
data class SmsVerification(
    @Id
    val phoneNumber: String,
    val randomNumber: String,
)

@Repository
interface SmsVerificationRedisRepository : CrudRepository<SmsVerification, String> {}