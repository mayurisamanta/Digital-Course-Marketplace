package com.kotlin.digital.course.repository

import com.kotlin.digital.course.entity.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository: JpaRepository<Course, Long>