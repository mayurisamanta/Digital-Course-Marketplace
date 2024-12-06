package com.kotlin.digital.course.service

import com.kotlin.digital.course.dto.UserReq

/**
 * Interface for JwtService
 */
interface JwtService {
    fun generateToken(userReq: UserReq): String
}