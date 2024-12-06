package com.kotlin.digital.course.entity

import com.kotlin.digital.course.enum.Role
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class UserInfo(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val emailId: String,

    val password: String,

    @Enumerated(EnumType.STRING)
    val role: Role,

    var lastLoginDate: LocalDateTime?
)

