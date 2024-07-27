package com.backend.speed_dating.auth.controller

import com.backend.speed_dating.auth.dto.SmsVerificationPayload
import com.backend.speed_dating.auth.dto.SmsVerificationVerifyPayload
import com.backend.speed_dating.auth.service.AuthService
import com.backend.speed_dating.common.dto.BaseResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
){

    @PostMapping("/sms-verification/request")
    fun sendSmsVerificationCode(
        @RequestBody @Valid payload : SmsVerificationPayload,
    ) : ResponseEntity<BaseResponse<Unit>>{
        authService.sendSmsVerificationCode(payload)

        val response = BaseResponse<Unit>(
            resultCode = "SUCCESS",
            data = null,
            message = "Verification SMS sent successfully"
        )
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PostMapping("/sms-verification/verify")
    fun verifySmsCode(
        @RequestBody @Valid payload : SmsVerificationVerifyPayload,
    ) : ResponseEntity<BaseResponse<Unit>>{
        val response = BaseResponse<Unit>(
            resultCode = "SUCCESS",
            data = null,
            message = "Verification SMS sent successfully"
        )
        return ResponseEntity(response, HttpStatus.CREATED)
    }
}