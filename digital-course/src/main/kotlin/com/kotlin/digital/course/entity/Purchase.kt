package com.kotlin.digital.course.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Purchase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    val course: Course,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    val user: UserInfo,

    val purchaseDate: LocalDateTime = LocalDateTime.now()
) {
}