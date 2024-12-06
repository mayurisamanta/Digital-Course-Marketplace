package com.kotlin.digital.course.repository

import com.kotlin.digital.course.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserInfo, Long> {
    fun findByEmailId(emailId: String): Optional<UserInfo>
}