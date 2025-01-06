package com.backend.speed_dating.chat.service

import com.backend.speed_dating.chat.dto.AgoraProfileAttributes
import com.backend.speed_dating.common.dto.UserToken
import com.backend.speed_dating.user.repository.MemberRepository
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
class AgoraService(
    private val memberRepository: MemberRepository,
) {

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

    fun registerChatUser(userToken: UserToken, chatUserName: String): String? {
        val orgName = appKey.split("#")[0]
        val appName = appKey.split("#")[1]
        val url = "http://$domain/$orgName/$appName/users"

        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.setBearerAuth(getAgoraChatAppTokenFromCache())

        val body = mapOf("username" to chatUserName, "password" to "123")
        val entity = HttpEntity(body, headers)

        val agoraProfileAttributes = AgoraProfileAttributes(
            nickname = userToken.nickname,
            avatarUrl = userToken.avatarUrl,
            phone = userToken.phone,
            gender = userToken.gender,
            birth = userToken.birth
        )

        return try {
            val response = restTemplate.exchange(url, HttpMethod.POST, entity, Map::class.java)
            val result = response.body?.get("entities") as? List<Map<String, Any>>
            val uuid = result?.get(0)?.get("uuid") as? String

            if (uuid != null) {
                // Set custom attributes for the user
                setUserAttributes(chatUserName, agoraProfileAttributes)
            }

            uuid
        } catch (ex: Exception) {
            throw RestClientException("register chat user error: ${ex.message}")
        }
    }

    fun getChatToken(userToken: UserToken, chatUserName: String): String {
        var chatUserUuid = getChatUserUuid(chatUserName)

        val agoraProfileAttributes = AgoraProfileAttributes(
            nickname = userToken.nickname,
            avatarUrl = userToken.avatarUrl,
            phone = userToken.phone,
            gender = userToken.gender,
            birth = userToken.birth
        )

        if (chatUserUuid == null) {
            chatUserUuid = registerChatUser(userToken, chatUserName)
            if (chatUserUuid == null) {
                throw RestClientException("Failed to register user")
            }
        }

        val builder = ChatTokenBuilder2()
        return builder.buildUserToken(appId, appcert, chatUserUuid, expire)
    }

    private fun setUserAttributes(
        chatUserName: String,
        agoraProfileAttributes: AgoraProfileAttributes,
    ){
        val orgName = appKey.split("#")[0]
        val appName = appKey.split("#")[1]
        val userAttributeSetUrl = "https://$domain/$orgName/$appName/metadata/user/$chatUserName"

        val headers: HttpHeaders = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            accept = listOf(MediaType.APPLICATION_JSON)
            setBearerAuth(getAgoraChatAppTokenFromCache())
        }

        val body = mapOf(
            "nickname" to agoraProfileAttributes.nickname,
            "avatarurl" to agoraProfileAttributes.avatarUrl.orEmpty(),
            "phone" to agoraProfileAttributes.phone.orEmpty(),
            "gender" to agoraProfileAttributes.gender.toString(),
            "birth" to agoraProfileAttributes.birth.orEmpty(),
        )

        val entity = HttpEntity(body, headers)

        try{
            restTemplate.exchange(userAttributeSetUrl, HttpMethod.PUT, entity, Map::class.java)
        }catch (ex : Exception){
            throw RestClientException("Failed to set user attributes: ${ex.message}")
        }





    }
}