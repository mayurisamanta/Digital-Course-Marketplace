package com.kotlin.digital.course.repository

import com.kotlin.digital.course.entity.Course
import com.kotlin.digital.course.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CourseRepository: JpaRepository<Course, Long> {
    fun findByCreator(user: UserInfo): List<Course>


    @Query("SELECT c FROM Course c WHERE c.title LIKE %:search% OR c.description LIKE %:search%")
    fun findAllWithSearch(search: String): List<Course>
}