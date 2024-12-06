package com.kotlin.digital.course.entity

import jakarta.persistence.*

@Entity
data class Course(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    val description: String,

    val price: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    val creator: UserInfo
)

