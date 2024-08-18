package com.backend.speed_dating.chat.controller

import com.backend.speed_dating.chat.service.AgoraService
import com.backend.speed_dating.common.dto.BaseResponse
import com.backend.speed_dating.common.status.ResultCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/chat")
@CrossOrigin
class AgoraChatTokenController(
    private val agoraService: AgoraService
) {

    @GetMapping("/app/token")
    fun getAppToken(): ResponseEntity<BaseResponse<String>> {
        return try {
            val token = agoraService.getAgoraAppToken()
            ResponseEntity.ok(
                BaseResponse(
                    resultCode = ResultCode.SUCCESS.name,
                    data = token,
                    message = ResultCode.SUCCESS.msg
                )
            )
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(
                BaseResponse(
                    resultCode = ResultCode.INVALID_REQUEST.name,
                    message = e.message ?: "Invalid request"
                )
            )
        }
    }

    @GetMapping("/user/{chatUserName}/token")
    fun getChatToken(@PathVariable chatUserName: String): ResponseEntity<BaseResponse<String>> {
        return try {
            val token = agoraService.getChatToken(chatUserName)
            ResponseEntity.ok(
                BaseResponse(
                    resultCode = ResultCode.SUCCESS.name,
                    data = token,
                    message = ResultCode.SUCCESS.msg
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(500).body(
                BaseResponse(
                    resultCode = ResultCode.SERVER_ERROR.name,
                    message = e.message ?: "Internal server error"
                )
            )
        }
    }
}