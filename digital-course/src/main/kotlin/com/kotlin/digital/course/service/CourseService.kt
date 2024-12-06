package com.kotlin.digital.course.service

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.CourseReq
import com.kotlin.digital.course.dto.UserSessionBean

interface CourseService {

    fun createCourse(courseReq: CourseReq, userSessionBean: UserSessionBean): ApiResp<*>

    fun getCourse(userSessionBean: UserSessionBean): ApiResp<*>
}