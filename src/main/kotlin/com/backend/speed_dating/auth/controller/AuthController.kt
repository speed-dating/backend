package com.backend.speed_dating.auth.controller

import com.backend.speed_dating.auth.dto.SmsVerificationPayload
import com.backend.speed_dating.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(

){
    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "str", description = "2번 반복할 문자열")
    @PostMapping("/sms-verification")
    fun verifySmsCode(@RequestBody @Valid payload : SmsVerificationPayload) : BaseResponse<Unit>{
        return BaseResponse(message = "ok!!")
    }
}