package com.kotlin.digital.course.filter

import com.kotlin.digital.course.constants.ApplicationConstants
import com.kotlin.digital.course.dto.UserSessionBean
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey

/**
 * This filter is used to validate the JWT token received in the request header
 */
class JWTTokenValidatorFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt: String? = request.getHeader(ApplicationConstants.JWT_HEADER)
        if (jwt != null) {
            try {
                val env: Environment = environment
                val secret =
                    env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE)
                val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
                val claims: Claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .body

                val username: String = claims["username"].toString()

                val authoritiesList: List<String> = claims["authorities"] as List<String>
                val authorities = AuthorityUtils.createAuthorityList(*authoritiesList.toTypedArray())

                val userSessionBeanMap: Map<String, Any> = claims["userSessionBean"] as Map<String, Any>
                val userSessionBean = UserSessionBean(
                    userId = userSessionBeanMap["userId"] as? Long,
                    emailId = userSessionBeanMap["emailId"] as? String
                )

                val authentication = UsernamePasswordAuthenticationToken(
                    username, null, authorities
                )
                authentication.details = userSessionBean

                SecurityContextHolder.getContext().authentication = authentication

            } catch (exception: Exception) {
                throw BadCredentialsException("Invalid Token received!")
            }
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath == "/login" || request.servletPath == "/register"
    }
}
