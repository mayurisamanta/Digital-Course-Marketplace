package com.kotlin.digital.course.config

import com.kotlin.digital.course.entity.UserInfo
import com.kotlin.digital.course.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/*
 * User details service to load the user details
 */
@Service
class AppUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    /*
     * Load the user details by username
     * @param username String
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val userInfo: UserInfo = userRepository.findByEmailId(username)
            .orElseThrow { UsernameNotFoundException("User details not found for the user: $username") }

        val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_${userInfo.role}"))

        return User(userInfo.emailId, userInfo.password, authorities)
    }
}