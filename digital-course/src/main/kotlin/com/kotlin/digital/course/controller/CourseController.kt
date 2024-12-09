package com.kotlin.digital.course.controller

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.CourseReq
import com.kotlin.digital.course.dto.UserSessionBean
import com.kotlin.digital.course.service.CourseService
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    /**
     * Create a new course
     *
     * @param courseReq
     */
    @PostMapping("/creator/course")
    fun createCourse(@RequestBody courseReq: CourseReq, authentication: Authentication): ResponseEntity<ApiResp<*>> {
        val userSessionBean = authentication.details as UserSessionBean
        log.info("Email: ${userSessionBean.emailId} -> Creating the course: $courseReq")
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(courseReq, userSessionBean))
    }

    /**
     * Get the course
     */
    @GetMapping("/creator/course")
    fun getCourse(authentication: Authentication): ResponseEntity<ApiResp<*>> {
        val userSessionBean = authentication.details as UserSessionBean
        log.info("Email: ${userSessionBean.emailId} -> Getting the course")
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourse(userSessionBean))
    }

    /**
     * Get all the courses
     */
    @GetMapping("/customer/course")
    fun getAllCourse(authentication: Authentication): ResponseEntity<ApiResp<*>> {
        val userSessionBean = authentication.details as UserSessionBean
        log.info("Email: ${userSessionBean.emailId} -> Getting all course")
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourse(userSessionBean))
    }

    /**
     * Purchase the course
     */
    @PostMapping("/customer/purchase/{courseId}")
    fun purchaseCourse(@PathVariable("courseId") courseId: Long, authentication: Authentication): ResponseEntity<ApiResp<*>> {
        val userSessionBean = authentication.details as UserSessionBean
        log.info("Email: ${userSessionBean.emailId} -> Purchasing the course: $courseId")
        return ResponseEntity.status(HttpStatus.OK).body(courseService.purchaseCourse(courseId, userSessionBean))
    }

    @GetMapping("/customer/purchase")
    fun getPurchaseCourse(authentication: Authentication): ResponseEntity<ApiResp<*>> {
        val userSessionBean = authentication.details as UserSessionBean
        log.info("Email: ${userSessionBean.emailId} -> Getting the purchased course")
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getPurchaseCourse(userSessionBean))
    }


}