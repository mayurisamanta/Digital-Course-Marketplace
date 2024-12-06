package com.kotlin.digital.course.service

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.UserReq
import com.kotlin.digital.course.entity.UserInfo

/**
 * interface for UserService
 */
interface UserService {

    fun register(userReq: UserReq): ApiResp<*>

    fun login(userReq: UserReq): ApiResp<*>

    fun getUserInfoByEmail(emailId: String): UserInfo

}