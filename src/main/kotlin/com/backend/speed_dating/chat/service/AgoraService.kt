package com.backend.speed_dating.chat.service

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import io.agora.chat.ChatTokenBuilder2
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

@Service
class AgoraService {

    @Value("\${agora.app.id}")
    private lateinit var appId: String

    @Value("\${agora.app.certificate}")
    private lateinit var appcert: String

    @Value("\${agora.token.expiration.seconds}")
    private var expire: Int = 0

    @Value("\${agora.app.key}")
    private lateinit var appKey: String

    @Value("\${agora.domain}")
    private lateinit var domain: String

    private val restTemplate: RestTemplate = RestTemplate()

    private lateinit var agoraChatAppTokenCache: Cache<String, String>

    init {
        agoraChatAppTokenCache = CacheBuilder.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(expire.toLong(), TimeUnit.SECONDS)
            .build()
    }

    fun getAgoraAppToken(): String {
        if (!StringUtils.hasText(appId) || !StringUtils.hasText(appcert)) {
            throw IllegalArgumentException("appid or appcert is not empty")
        }
        val builder = ChatTokenBuilder2()
        return builder.buildAppToken(appId, appcert, expire)
    }

    fun getAgoraChatAppTokenFromCache(): String {
        return try {
            agoraChatAppTokenCache.get("agora-chat-app-token") { getAgoraAppToken() }
        } catch (ex: Exception) {
            throw IllegalArgumentException("Get Agora Chat app token from cache error")
        }
    }

    fun getChatUserUuid(chatUserName: String): String? {
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
            results?.get(0)?.get("uuid") as? String
        } catch (e: Exception) {
            println("get chat user error: ${e.message}")
            null
        }
    }

    fun registerChatUser(chatUserName: String): String? {
        val orgName = appKey.split("#")[0]
        val appName = appKey.split("#")[1]
        val url = "http://$domain/$orgName/$appName/users"

        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.setBearerAuth(getAgoraChatAppTokenFromCache())

        val body = mapOf("username" to chatUserName, "password" to "123")
        val entity = HttpEntity(body, headers)

        return try {
            val response = restTemplate.exchange(url, HttpMethod.POST, entity, Map::class.java)
            val result = response.body?.get("entities") as? List<Map<String, Any>>
            result?.get(0)?.get("uuid") as? String
        } catch (ex: Exception) {
            throw RestClientException("register chat user error: ${ex.message}")
        }
    }

    fun getChatToken(chatUserName: String): String {
        var chatUserUuid = getChatUserUuid(chatUserName)

        if (chatUserUuid == null) {
            chatUserUuid = registerChatUser(chatUserName)
            if (chatUserUuid == null) {
                throw RestClientException("Failed to register user")
            }
        }

        val builder = ChatTokenBuilder2()
        return builder.buildUserToken(appId, appcert, chatUserUuid, expire)
    }
}