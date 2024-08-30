package com.backend.speed_dating.common.authority

import com.backend.speed_dating.common.dto.UserToken
import com.backend.speed_dating.common.status.Gender
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.Date

const val EXPIRATION_MILLISECONDS : Long = 1000 * 60 * 30

@Component
class JwtTokenProvider () {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    fun createToken(userToken: UserToken): TokenInfo {
        val authorities = "ROLE_USER"  // Or fetch roles/authorities if needed

        val now = Date()
        val accessTokenExpiredAt = Date(now.time + EXPIRATION_MILLISECONDS)

        val accessToken = Jwts
            .builder()
            .subject(userToken.id.toString())
            .claim("id", userToken.id.toString())
            .claim("nickname", userToken.nickname)
            .claim("avatarUrl", userToken.avatarUrl)
            .claim("phone", userToken.phone)
            .claim("gender", userToken.gender.ordinal)  // Assuming gender is an enum
            .claim("birth", userToken.birth)
            .claim("auth", authorities)
            .issuedAt(now)
            .setExpiration(accessTokenExpiredAt)
            .signWith(key)
            .compact()

        return TokenInfo(
            grantType = "Bearer",
            accessToken = accessToken,
        )
    }

    fun getUserTokenFromJwt(token: String): UserToken{
        val claims = getClaims(token)

        return UserToken(
            id = claims.subject.toLong(),
            nickname = claims["nickname"] as String,
            avatarUrl = claims["avatarUrl"] as String,
            phone = claims["phone"] as String,
            gender = Gender.entries[claims["gender"] as Int],
            birth = claims["birth"] as String
        )
    }

    fun getAuthentication(token : String) : Authentication {
        val claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException("Invalid Token")

        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails = User(
            claims.subject, "", authorities
        )
        val authToken = UsernamePasswordAuthenticationToken(principal, "", authorities)
        authToken.details = claims

        return authToken
    }

    fun validateToken(token : String ) : Boolean {
        try{
            getClaims(token)
            return true
        } catch (e: Exception) {
            when(e){
                is SecurityException -> {}
                is MalformedJwtException -> {}
                is ExpiredJwtException -> {}
                is UnsupportedJwtException -> {}
                is IllegalArgumentException -> {}
                else -> {}
            }
            println(e.message)
        }
        return false
    }

    private fun getClaims(token : String) : Claims =
        Jwts
            .parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

}