package com.kotlin.digital.course.service.impl

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.UserReq
import com.kotlin.digital.course.dto.UserSessionBean
import com.kotlin.digital.course.entity.UserInfo
import com.kotlin.digital.course.enum.Role
import com.kotlin.digital.course.exception.UserException
import com.kotlin.digital.course.repository.UserRepository
import com.kotlin.digital.course.service.UserService
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * UserServiceImpl
 */
@Service
@Slf4j
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    /**
     * Register a new user
     *
     * @param userReq
     */
    @Transactional
    override fun register(userReq: UserReq): ApiResp<*> {
        val emailId = userReq.emailId

        userRepository.findByEmailId(emailId).ifPresent {
            throw RuntimeException("User already exists with the email id: $emailId")
        }

        return try {
            val hashedPassword = passwordEncoder.encode(userReq.password)
            var user = userReq.role?.let {
                UserInfo(
                    emailId = userReq.emailId,
                    password = hashedPassword,
                    role = it,
                    lastLoginDate = null
                )
            }
            if (user != null) {
                user = userRepository.save(user)
            }

            ApiResp(
                status = HttpStatus.CREATED.value(),
                message = "User registered successfully",
                data = user
            )
        } catch (e: Exception) {
            log.error("Email: ${userReq.emailId} -> Error while registering the user: ${e.message}")
            throw RuntimeException("Error while registering the user: ${e.message}")
        }
    }

    @Transactional
    override fun login(userReq: UserReq): ApiResp<*> {
        return try {
            userRepository.updateLastLoginAt(userReq.emailId)
            ApiResp(
                status = HttpStatus.OK.value(),
                message = "Login Successful",
                data = null
            )
        } catch (e: Exception) {
            log.error("Email: ${userReq.emailId} -> Error while logging in the user: ${e.message}")
            throw RuntimeException("Error while logging in the user: ${e.message}")
        }
    }

    /**
     * Get user info by email id
     *
     * @param emailId email id
     * @return user info
     */
    override fun getUserInfoByEmail(emailId: String): UserInfo {

        try {
            return userRepository.findByEmailId(emailId)
                .orElseThrow { UserException("User not found with the email id: $emailId") }
        } catch (e: Exception) {
            log.error("Email: $emailId -> Error while fetching the user info: ${e.message}")
            throw RuntimeException("Error while fetching the user info: ${e.message}")
        }
    }

    override fun getAllUsers(userSessionBean: UserSessionBean): ApiResp<*> {
        return try {
            log.info("Email: ${userSessionBean.emailId} -> Getting all users")
            val roles = listOf(Role.CREATOR, Role.CUSTOMER)
            val users = userRepository.findByRoleIn(roles)
            ApiResp(
                status = HttpStatus.OK.value(),
                message = "Users fetched successfully",
                data = users
            )
        } catch (e: Exception) {
            log.error("Email: ${userSessionBean.emailId} -> Error while fetching the users: ${e.message}")
            throw RuntimeException("Error while fetching the users: ${e.message}")
        }
    }

}