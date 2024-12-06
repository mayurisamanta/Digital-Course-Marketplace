package com.kotlin.digital.course.filter

import jakarta.servlet.*
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

import java.io.IOException

/**
 * This filter is used to log the user authentication details after the authentication is successful
 */
@Slf4j
class AuthoritiesLoggingAfterFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            log.info("User ${authentication.name} is successfully authenticated and has the authorities ${authentication.authorities}")
        }
        chain.doFilter(request, response)
    }
}