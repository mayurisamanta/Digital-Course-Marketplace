package com.kotlin.digital.course.dto

import com.kotlin.digital.course.constants.ApplicationConstants
import com.kotlin.digital.course.enum.Role
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

/**
 * Data class for handling user requests
 */
data class UserReq(
    @field:Pattern(
        regexp = ApplicationConstants.PASSWORD_REGEX,
        message = "Password should contain at least 1 uppercase, 1 lowercase, 1 number, 1 special character and should be of minimum 8 characters"
    )
    val password: String,

    @field:Pattern(
        regexp = ApplicationConstants.EMAIL_REGEX,
        message = "Please enter valid email Id"
    )
    val emailId: String,

    @field:NotNull(message = "Role should not be null")
    val role: Role?
)