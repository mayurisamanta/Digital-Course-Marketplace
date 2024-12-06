package com.kotlin.digital.course.config

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/*
 * Authentication provider to authenticate the user
 */
@Component
class AppAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

    /*
     * Authenticate the user
     * @param authentication Authentication
     * @return Authentication
     * @throws AuthenticationException
     */
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
        if (passwordEncoder.matches(password, userDetails.password)) {
            return UsernamePasswordAuthenticationToken(username, password, userDetails.authorities)
        } else {
            throw BadCredentialsException("Invalid password!")
        }
    }

    /*
     * Check if the authentication is supported
     * @param authentication Class<*>
     * @return boolean
     */
    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}