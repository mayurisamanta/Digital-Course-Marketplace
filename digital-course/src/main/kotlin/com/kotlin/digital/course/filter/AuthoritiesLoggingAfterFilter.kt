package com.kotlin.digital.course.filter

import jakarta.servlet.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

import java.io.IOException

/**
 * This filter is used to log the user authentication details after the authentication is successful
 */
class AuthoritiesLoggingAfterFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            println("User ${authentication.name} is successfully authenticated and has the authorities ${authentication.authorities}")
        }
        chain.doFilter(request, response)
    }
}