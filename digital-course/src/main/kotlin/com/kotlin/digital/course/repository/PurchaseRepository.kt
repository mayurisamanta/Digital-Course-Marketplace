package com.kotlin.digital.course.repository

import com.kotlin.digital.course.entity.Course
import com.kotlin.digital.course.entity.Purchase
import com.kotlin.digital.course.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PurchaseRepository: JpaRepository<Purchase, Long> {

    @Query("SELECT p.course FROM Purchase p WHERE p.user = :user")
    fun findCoursesByUser(user: UserInfo): List<Course>
}