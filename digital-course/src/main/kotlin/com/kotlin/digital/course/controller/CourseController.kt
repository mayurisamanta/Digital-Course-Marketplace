package com.kotlin.digital.course.controller

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.CourseReq
import com.kotlin.digital.course.dto.UserSessionBean
import com.kotlin.digital.course.service.CourseService
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * CourseController
 */
@RestController
@Slf4j
class CourseController(
    private val courseService: CourseService
) {

    @PostMapping("/course")
    @PreAuthorize("hasRole('ROLE_CREATOR')")
    fun createCourse(@RequestBody courseReq: CourseReq, authentication: Authentication): ResponseEntity<ApiResp<*>> {
        val userSessionBean = authentication.details as UserSessionBean
        log.info("Email: ${userSessionBean.emailId} -> Creating the course: $courseReq")
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(courseReq, userSessionBean))
    }


}