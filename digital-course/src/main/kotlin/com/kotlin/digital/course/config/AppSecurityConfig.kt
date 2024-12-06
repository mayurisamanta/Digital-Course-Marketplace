package com.kotlin.digital.course.config

import com.kotlin.digital.course.exception.CustomAccessDeniedHandler
import com.kotlin.digital.course.exception.CustomBasicAuthenticationEntryPoint
import com.kotlin.digital.course.filter.AuthoritiesLoggingAfterFilter
import com.kotlin.digital.course.filter.AuthoritiesLoggingAtFilter
import com.kotlin.digital.course.filter.JWTTokenValidatorFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.password.CompromisedPasswordChecker
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

/*
 * Configuration class to configure the security
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AppSecurityConfig(
    private val userDetailsService: UserDetailsService
) {

    companion object {
        private val WHITE_LISTED_APIS = arrayOf("/login", "/register", "/h2-console/**")
        private val CREATOR_APIS = arrayOf("/creator/**")
        private val CUSTOMER_APIS = arrayOf("/customer/**")
    }

    /*
     * Security filter chain to configure the security
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .addFilterAfter(AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter::class.java)
            .addFilterAt(AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(*WHITE_LISTED_APIS).permitAll()
                    .requestMatchers(*CREATOR_APIS).hasAnyRole("CREATOR")
                    .requestMatchers(*CUSTOMER_APIS).hasAnyRole("CUSTOMER")
                    .anyRequest().authenticated()
            }
            .sessionManagement { sessionConfig ->
                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(JWTTokenValidatorFilter(), BasicAuthenticationFilter::class.java)
        http.httpBasic { hbc -> hbc.authenticationEntryPoint(CustomBasicAuthenticationEntryPoint()) }
        http.exceptionHandling { ehc -> ehc.accessDeniedHandler(CustomAccessDeniedHandler()) }

        http.headers { headers -> headers.frameOptions { it.sameOrigin() } }
        return http.build()
    }

    /*
     * Password encoder for encoding and matching passwords
     * @return PasswordEncoder
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    /*
     * Password checker to check if the password is compromised
     * @return CompromisedPasswordChecker
     */
    @Bean
    fun compromisedPasswordChecker(): CompromisedPasswordChecker {
        return HaveIBeenPwnedRestApiPasswordChecker()
    }

    /*
     * Authentication manager to authenticate the user
     * @param userDetailsService UserDetailsService
     * @param passwordEncoder PasswordEncoder
     * @return AuthenticationManager
     */
    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authenticationProvider = AppAuthenticationProvider(userDetailsService, passwordEncoder())
        val providerManager = ProviderManager(authenticationProvider)
        return providerManager
    }
}