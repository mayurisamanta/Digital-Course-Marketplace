package com.kotlin.digital.course.service.impl

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.UserReq
import com.kotlin.digital.course.entity.UserInfo
import com.kotlin.digital.course.exception.UserException
import com.kotlin.digital.course.repository.UserRepository
import com.kotlin.digital.course.service.UserService
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun register(userReq: UserReq): ApiResp<*> {
        val emailId = userReq.emailId

        userRepository.findByEmailId(emailId).ifPresent {
            throw RuntimeException("User already exists with the email id: $emailId")
        }

        return try {
            val hashedPassword = passwordEncoder.encode(userReq.password)
            val user = userReq.role?.let {
                UserInfo(
                    emailId = userReq.emailId,
                    password = hashedPassword,
                    role = it,
                )
            }
            if (user != null) {
                userRepository.save(user)
            }

            ApiResp(
                status = HttpStatus.OK.value(),
                message = "User registered successfully",
                data = user
            )
        } catch (e: Exception) {
            log.error("Error while registering the user: ${e.message}")
            throw RuntimeException("Error while registering the user: ${e.message}")
        }
    }

    /*@Transactional
    override fun login(userReq: UserReq): ApiResp<*> {
        return try {
            userRepository.updateLastLoginAt(userReq.emailId)
            ApiResp(
                status = HttpStatus.CREATED.value(),
                message = "Login Successful"
            )
        } catch (e: Exception) {
            throw RuntimeException("Error while logging in the user: ${e.message}")
        }
    }*/

    /**
     * Get user info by email id
     *
     * @param emailId email id
     * @return user info
     */
    override fun getUserInfoByEmail(emailId: String): UserInfo {
        return userRepository.findByEmailId(emailId)
            .orElseThrow { UserException("User not found with the email id: $emailId") }
    }

}