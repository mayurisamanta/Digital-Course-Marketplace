package com.kotlin.digital.course.service.impl

import com.kotlin.digital.course.constants.ApplicationConstants
import com.kotlin.digital.course.dto.UserReq
import com.kotlin.digital.course.dto.UserSessionBean
import com.kotlin.digital.course.entity.UserInfo
import com.kotlin.digital.course.service.JwtService
import com.kotlin.digital.course.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

/**
 * JwtServiceImpl
 */
@Service
@Slf4j
class JwtServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val env: Environment,
    private val userService: UserService
) : JwtService {

    /**
     * Generate JWT token
     *
     * @param userReq
     */
    override fun generateToken(userReq: UserReq): String {
        val emailId = userReq.emailId
        val userInfo: UserInfo = userService.getUserInfoByEmail(emailId)
        log.info("Email: $emailId -> User Info: $userInfo")

        return try {
            var jwt = ""
            val authentication = UsernamePasswordAuthenticationToken.unauthenticated(userReq.emailId, userReq.password)
            val authenticationResponse: Authentication = authenticationManager.authenticate(authentication)
            log.info("Email: $emailId -> Authentication response: ${authenticationResponse.isAuthenticated}")

            if (authenticationResponse.isAuthenticated) {
                val secret = env.getProperty(
                    ApplicationConstants.JWT_SECRET_KEY,
                    ApplicationConstants.JWT_SECRET_DEFAULT_VALUE
                )

                val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
                val userSessionBean = UserSessionBean(
                    userId = userInfo.id,
                    emailId = userInfo.emailId
                )

                log.info("Email: $emailId -> UserSessionBean: $userSessionBean")

                jwt = Jwts.builder()
                    .setIssuer("Notion")
                    .setSubject("JWT Token")
                    .claim("username", authenticationResponse.name)
                    .claim(
                        "authorities",
                        authenticationResponse.authorities.joinToString(",") { it.authority }
                    )
                    .claim("userSessionBean", userSessionBean)
                    .setIssuedAt(Date())
                    .setExpiration(Date(System.currentTimeMillis() + ApplicationConstants.TIME_OUT)) // 24 hours
                    .signWith(secretKey)
                    .compact()

                log.info("Email: $emailId -> JWT Token generated successfully")
            }
            jwt
        } catch (e: Exception) {
            log.error("Email: $emailId -> Error while generating the token: ${e.message}")
            throw RuntimeException("Error while generating the token for email $emailId: ${e.message}")
        }
    }
}