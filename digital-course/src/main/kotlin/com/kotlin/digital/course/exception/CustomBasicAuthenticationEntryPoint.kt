package com.kotlin.digital.course.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.digital.course.dto.ApiResp
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException

/**
 * CustomBasicAuthenticationEntryPoint for handling authentication failure
 */
class CustomBasicAuthenticationEntryPoint : AuthenticationEntryPoint {

    /**
     * Commence
     *
     * @param request       HttpServletRequest
     * @param response      HttpServletResponse
     * @param authException AuthenticationException
     * @throws IOException IOException
     */
    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val message = authException.message ?: "Unauthorized"
        response.setHeader("eazybank-error-reason", "Authentication failed")
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json;charset=UTF-8"

        val apiResp = ApiResp(
            status = HttpStatus.FORBIDDEN.value(),
            error = HttpStatus.FORBIDDEN.reasonPhrase,
            message = message,
            data = null
        )

        val jsonResponse = ObjectMapper().writeValueAsString(apiResp)
        response.writer.write(jsonResponse)
    }
}