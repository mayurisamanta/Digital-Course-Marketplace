package com.kotlin.digital.course.service.impl

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.CourseReq
import com.kotlin.digital.course.dto.UserSessionBean
import com.kotlin.digital.course.entity.Course
import com.kotlin.digital.course.repository.CourseRepository
import com.kotlin.digital.course.service.CourseService
import com.kotlin.digital.course.service.UserService
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

/**
 * CourseServiceImpl
 */
@Service
class CourseServiceImpl (
    private val courseRepository: CourseRepository,
    private val userService: UserService
): CourseService {

    override fun createCourse(courseReq: CourseReq, userSessionBean: UserSessionBean): ApiResp<*> {

        val emailId: String = userSessionBean.emailId ?: throw RuntimeException("Session expired, Please login again")

        log.info("Email: $emailId -> Creating the course: $courseReq")

        val user = userService.getUserInfoByEmail(emailId)

        var course = Course(
            title = courseReq.title,
            description = courseReq.description,
            creator = user,
            price = courseReq.price
        )

        course = courseRepository.save(course)

        log.info("Email: $emailId -> Course created successfully: $course")

        return ApiResp(
            status = HttpStatus.CREATED.value(),
            message = "Course created successfully",
            data = course
        )

    }

    override fun getCourse(userSessionBean: UserSessionBean): ApiResp<*> {

        val emailId: String = userSessionBean.emailId ?: throw RuntimeException("Session expired, Please login again")

        log.info("Email: $emailId -> Getting the course")

        val user = userService.getUserInfoByEmail(emailId)

        val courseList = courseRepository.findByCreator(user)

        log.info("Email: $emailId -> Course list: $courseList")

        return ApiResp(
            status = HttpStatus.OK.value(),
            message = "Course list",
            data = courseList
        )
    }
}