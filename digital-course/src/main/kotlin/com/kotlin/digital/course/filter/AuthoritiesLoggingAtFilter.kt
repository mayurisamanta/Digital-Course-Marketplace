package com.kotlin.digital.course.filter


import jakarta.servlet.*
import java.io.IOException

/**
 * This filter is used to log the user authentication details before the authentication is successful
 */
class AuthoritiesLoggingAtFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        println("Authentication Validation is in progress")
        chain.doFilter(request, response)
    }
}