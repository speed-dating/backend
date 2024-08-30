package com.backend.speed_dating.auth.service

import com.backend.speed_dating.auth.dto.SmsVerificationPayload
import com.backend.speed_dating.auth.dto.SmsVerificationVerifyPayload
import com.backend.speed_dating.auth.model.response.UserVerificationResponse
import com.backend.speed_dating.common.authority.JwtTokenProvider
import com.backend.speed_dating.common.dto.UserToken
import com.backend.speed_dating.common.service.SmsService
import com.backend.speed_dating.redis.repository.SmsVerification
import com.backend.speed_dating.redis.repository.SmsVerificationRedisRepository
import com.backend.speed_dating.user.dto.UserResponseModel
import com.backend.speed_dating.user.repository.MemberRepository
import com.backend.speed_dating.user.repository.UserRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import com.backend.speed_dating.common.exception.InvalidVerificationCodeException as InvalidVerificationCodeException1

@Service
class AuthService(
    private val smsService: SmsService,
    private val smsVerificationRedisRepository : SmsVerificationRedisRepository,
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider,
){

    fun sendSmsVerificationCode(payload: SmsVerificationPayload){
        val randomCode = generateRandomCode()

        smsVerificationRedisRepository.save(
            SmsVerification(
                phoneNumber = payload.phoneNumber,
                randomNumber = "1111",
                )
        )
        val temp = smsVerificationRedisRepository.findById(payload.phoneNumber)

        val content = "[Speed-Dating]\n" +
                "verify code : ${randomCode}"
        val utf8Content = String(content.toByteArray(Charsets.UTF_8))

        smsService.sendSms(
            payload.phoneNumber,
            payload.countryCode,
            utf8Content,
        )
    }

    @Transactional
    fun verifySmsCode(payload: SmsVerificationVerifyPayload) : UserVerificationResponse{
        val storedVerification = smsVerificationRedisRepository.findById(payload.phoneNumber)

        if (storedVerification.isEmpty) {
            throw InvalidVerificationCodeException1("phoneNumber", "No verification code found for phone number ${payload.phoneNumber}")
        }

        val storedCode = storedVerification.get().randomNumber

        if (storedCode != payload.verifyCode) {
            throw InvalidVerificationCodeException1("phoneNumber", "No verification code found for phone number ${payload.phoneNumber}")
        }

        smsVerificationRedisRepository.deleteById(payload.phoneNumber)
        val member = memberRepository.findByPhoneNumber(payload.phoneNumber)

        return if(member!=null){
            val token = jwtTokenProvider.createToken(
                userToken = UserToken(
                    id = member.id!!,
                    nickname = member.nickname,
                    avatarUrl = member.profileImageUrl,
                    phone = member.phoneNumber,
                    gender = member.gender,
                    birth = member.birthDate.toString(),
                    )
            )
            return UserVerificationResponse(
                token = token,
                user = UserResponseModel.of(member),
            )
        }else {
            UserVerificationResponse(
                token = null,
                user = null,
            )
        }
    }

    private fun generateRandomCode(): String {
        val random = SecureRandom()
        val code = random.nextInt(10000) // 0부터 9999까지의 랜덤 숫자 생성
        return String.format("%04d", code) // 4자리 문자열로 포맷팅
    }
}