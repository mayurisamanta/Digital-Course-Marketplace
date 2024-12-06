package com.kotlin.digital.course.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.digital.course.dto.ApiResp
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException

/**
 * Custom Access Denied Handler
 */
class CustomAccessDeniedHandler : AccessDeniedHandler {

    /**
     * Handle access denied exception
     *
     * @param request               HttpServletRequest
     * @param response              HttpServletResponse
     * @param accessDeniedException AccessDeniedException
     * @throws IOException IOException
     */
    @Throws(IOException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val message = accessDeniedException.message ?: "Authorization failed"
        response.setHeader("notion-denied-reason", "Authorization failed")
        response.status = HttpStatus.FORBIDDEN.value()
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