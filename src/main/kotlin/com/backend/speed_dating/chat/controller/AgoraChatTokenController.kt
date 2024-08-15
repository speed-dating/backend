package com.backend.speed_dating.chat.controller

import com.backend.speed_dating.chat.dto.payload.ChatRoomCreationPayload
import com.backend.speed_dating.chat.service.ChatService
import com.backend.speed_dating.common.dto.BaseResponse
import com.backend.speed_dating.common.status.ResultCode
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import io.agora.chat.ChatTokenBuilder2
import io.jsonwebtoken.Header
import io.netty.handler.codec.Headers
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit


@RestController()
@RequestMapping("/api/v1/chat")
@CrossOrigin
class AgoraChatTokenController(

){
    @Value("\${agora.app.id}")
    private lateinit var appId: String

    @Value("\${agora.app.certificate}")
    private lateinit var appcert: String

    @Value("\${agora.token.expiration.seconds}")
    private var expire: Int = 0

    @Value("\${agora.app.key}")
    private lateinit var appKey : String

    @Value("\${agora.domain}")
    private lateinit var domain : String

    private val restTemplate : RestTemplate = RestTemplate()

    private lateinit var agoraChatAppTokenCache : Cache<String, String>

    @PostConstruct
    fun init(){
        agoraChatAppTokenCache = CacheBuilder.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(expire.toLong(), TimeUnit.SECONDS)
            .build()
    }

    /***
     * Get a agora web token
     *
     * @return app privileges token
     */
    @GetMapping("/app/token")
    fun getAppToken() : ResponseEntity<BaseResponse<String>> {
        return if (!StringUtils.hasText(appId) or !StringUtils.hasText(appcert)){
            ResponseEntity.badRequest().body(
                BaseResponse(
                    resultCode = ResultCode.INVALID_REQUEST.name,
                    message = "appid or appcert is empty",
                    )
            )
        }else{
            ResponseEntity.ok().body(
                BaseResponse(
                    resultCode = ResultCode.SUCCESS.name,
                    message = getAgoraAppToken(),
                    )
            )
        }
    }

    @GetMapping("/user/{chatUserName}/token")
    fun getChatToken(@PathVariable chatUserName: String) : ResponseEntity<BaseResponse<String>> {
        if (!StringUtils.hasText(appId) || !StringUtils.hasText(appcert)) {
            return ResponseEntity.badRequest().body(
                BaseResponse(
                    resultCode = ResultCode.INVALID_REQUEST.name,
                    message = "appid or appcert is empty"
                )
            )
        }

        if (!StringUtils.hasText(appKey) || !StringUtils.hasText(domain)) {
            return ResponseEntity.badRequest().body(
                BaseResponse(
                    resultCode = ResultCode.INVALID_REQUEST.name,
                    message = "appkey or domain is empty"
                )
            )
        }

        if (!appKey.contains("#")) {
            return ResponseEntity.badRequest().body(
                BaseResponse(
                    resultCode = ResultCode.INVALID_REQUEST.name,
                    message = "appkey is illegal"
                )
            )
        }

        if (!StringUtils.hasText(chatUserName)) {
            return ResponseEntity.badRequest().body(
                BaseResponse(
                    resultCode = ResultCode.INVALID_REQUEST.name,
                    message = "chatUserName is empty"
                )
            )
        }

        val builder = ChatTokenBuilder2()

        var chatUserUuid = getChatUserUuid(chatUserName)

        if (chatUserUuid == null) {
            chatUserUuid = registerChatUser(chatUserName)
            if (chatUserUuid == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    BaseResponse(
                        resultCode = ResultCode.SERVER_ERROR.name,
                        message = "Failed to register user"
                    )
                )
            }
        }

        return ResponseEntity.ok(
            BaseResponse(
                resultCode = ResultCode.SUCCESS.name,
                data = builder.buildUserToken(appId, appcert, chatUserUuid, expire),
                message = ResultCode.SUCCESS.msg
            )
        )

    }

    private fun getChatUserUuid(chatUserName: String) : String? {
        val orgName = appKey.split("#")[0]
        val appName = appKey.split("#")[1]
        val url = "http://$domain/$orgName/$appName/users/$chatUserName"

        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.setBearerAuth(getAgoraChatAppTokenFromCache())

        val entity = HttpEntity<Map<String, String>>(null, headers)

        return try {
            val responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map::class.java)
            val results = responseEntity.body?.get("entities") as? List<Map<String, Any>>
            println(results)
            results?.get(0)?.get("uuid") as? String
        } catch (e: Exception) {
            println("get chat user error: ${e.message}")
            null
        }
    }

    private fun registerChatUser(chatUserName: String) : String? {
        val orgName = appKey.split("#")[0]
        val appName = appKey.split("#")[1]
        val url = "http://$domain/$orgName/$appName/users"

        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.setBearerAuth(getAgoraChatAppTokenFromCache())

        val body = mapOf("username" to chatUserName, "password" to 123)
        val entity = HttpEntity(body, headers)

        return try {
            val response = restTemplate.exchange(url, HttpMethod.POST, entity, Map::class.java)
            val result = response.body?.get("entities") as? List<Map<String, Any>>
            result?.get(0)?.get("uuid") as? String
        }catch (ex : Exception) {
            throw RestClientException("register chat user error: ${ex.message}")
        }
    }

    private fun getAgoraAppToken() : String {
        if(!StringUtils.hasText(appId) || !StringUtils.hasText(appcert)){
            throw IllegalArgumentException("appid or appcert is not empty")
        }
        val builder = ChatTokenBuilder2()
        return builder.buildAppToken(appId, appcert, expire)
    }

    private fun getAgoraChatAppTokenFromCache(): String {
        return try{
            agoraChatAppTokenCache.get("agora-chat-app-token") {getAgoraAppToken()}
        }catch (ex : Exception){
            throw IllegalArgumentException("Get Agora Chat app token from cache error")
        }
    }

}