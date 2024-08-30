package com.backend.speed_dating.user.controller

import com.backend.speed_dating.common.annotation.UserTokenAnnotation
import com.backend.speed_dating.common.dto.BaseResponse
import com.backend.speed_dating.common.dto.UserToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.backend.speed_dating.user.dto.UserCreationDto
import com.backend.speed_dating.user.model.response.ProfileResponseModel
import com.backend.speed_dating.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/v1/user")
class UserController (
    private val userService: UserService,
){
    @GetMapping("test")
    fun test() : String = "hi"

    @PostMapping("/signup")
    fun signup(@RequestBody @Valid payload : UserCreationDto) : ResponseEntity<BaseResponse<Unit>> {
        val resultMsg = userService.signup(payload)
        val response = BaseResponse<Unit>(
            resultCode = "SUCCESS",
            data = null,
            message = resultMsg,
        )
        return ResponseEntity(response, HttpStatus.CREATED)
    }



    @GetMapping("/profile/me")
    fun getMyProfile(@UserTokenAnnotation userToken: UserToken) : ResponseEntity<BaseResponse<ProfileResponseModel>>{
        val result = userService.getProfile(userToken.id)
        val response = BaseResponse<ProfileResponseModel>(
            resultCode = "SUCCESS",
            data = result,
            message = "ok!!",
        )
        return ResponseEntity(response, HttpStatus.OK)
    }
}
