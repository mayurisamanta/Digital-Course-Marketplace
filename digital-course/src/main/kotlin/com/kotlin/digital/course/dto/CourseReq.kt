package com.kotlin.digital.course.dto

import jakarta.validation.constraints.Size

/**
 * CourseReq data class
 */
data class CourseReq(

    @field:Size(min = 3, max = 100, message = "Course title must be between 3 and 100 characters")
    val title: String,

    @field:Size(min = 10, message = "Course description must be at least 10 characters")
    val description: String,

    val price: Double
)
