package com.kotlin.digital.course.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kotlin.digital.course.enum.Role
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class UserInfo(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val emailId: String,

    @JsonIgnore
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: Role,

    var lastLoginDate: LocalDateTime?
)

