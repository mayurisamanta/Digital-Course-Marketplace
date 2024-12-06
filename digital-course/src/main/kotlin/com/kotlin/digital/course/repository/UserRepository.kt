package com.kotlin.digital.course.repository

import com.kotlin.digital.course.entity.UserInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<UserInfo, Long> {
    fun findByEmailId(emailId: String): Optional<UserInfo>

    @Modifying
    @Transactional
    @Query("update UserInfo u set u.lastLoginDate = current_timestamp where u.emailId = :emailId")
    fun updateLastLoginAt(emailId: String)
}