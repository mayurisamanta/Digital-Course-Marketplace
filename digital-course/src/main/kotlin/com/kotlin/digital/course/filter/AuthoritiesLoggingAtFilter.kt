package com.kotlin.digital.course.filter


import jakarta.servlet.*
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import java.io.IOException

/**
 * This filter is used to log the user authentication details before the authentication is successful
 */
@Slf4j
class AuthoritiesLoggingAtFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        log.info("Authentication Validation is in progress")
        chain.doFilter(request, response)
    }
}