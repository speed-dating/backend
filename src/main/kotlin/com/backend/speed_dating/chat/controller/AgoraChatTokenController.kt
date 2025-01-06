package com.backend.speed_dating.chat.controller

import com.backend.speed_dating.chat.service.AgoraService
import com.backend.speed_dating.common.dto.BaseResponse
import com.backend.speed_dating.common.dto.UserToken
import com.backend.speed_dating.common.status.Gender
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
        val fakeUserToken = UserToken(
            id = 1,
            nickname = "shy",
            phone = "01099645997",
            avatarUrl = "https://images.unsplash.com/photo-1485811901755-d0afa3c218f5?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDEwfGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
            gender = Gender.MALE,
            birth = "970328"
        )

        return try {
            val token = agoraService.getChatToken(fakeUserToken, chatUserName)
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