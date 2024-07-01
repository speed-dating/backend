package com.backend.speed_dating.user.controller

import com.backend.speed_dating.common.authority.TokenInfo
import com.backend.speed_dating.common.dto.BaseResponse
import com.backend.speed_dating.user.dto.LoginDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.backend.speed_dating.user.dto.UserCreationDto
import com.backend.speed_dating.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/v1/user")
class UserController (
    private val userService: UserService,
){
    @GetMapping("test")
    fun test() : String = "hi"

    @PostMapping("/signup")
    fun signup(@RequestBody @Valid payload : UserCreationDto) : BaseResponse<Unit> {
        val resultMsg = userService.signup(payload)
        return BaseResponse(message = resultMsg)
    }

    @PostMapping("signIn")
    fun signIn(@RequestBody @Valid payload: LoginDto) : BaseResponse<TokenInfo> {
        val tokenInfo = userService.signIn(payload)
        return BaseResponse(data = tokenInfo)
    }
}
