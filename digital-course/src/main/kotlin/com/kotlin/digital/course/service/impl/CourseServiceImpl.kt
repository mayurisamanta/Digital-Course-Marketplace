package com.kotlin.digital.course.service.impl

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.CourseReq
import com.kotlin.digital.course.dto.UserSessionBean
import com.kotlin.digital.course.entity.Course
import com.kotlin.digital.course.entity.Purchase
import com.kotlin.digital.course.exception.CourseException
import com.kotlin.digital.course.repository.CourseRepository
import com.kotlin.digital.course.repository.PurchaseRepository
import com.kotlin.digital.course.service.CourseService
import com.kotlin.digital.course.service.UserService
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

/**
 * CourseServiceImpl
 */
@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val userService: UserService,
    private val purchaseRepository: PurchaseRepository
) : CourseService {

    /**
     * Create a new course
     *
     * @param courseReq
     */
    override fun createCourse(courseReq: CourseReq, userSessionBean: UserSessionBean): ApiResp<*> {

        try {
            val emailId: String =
                userSessionBean.emailId ?: throw RuntimeException("Session expired, Please login again")

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
        } catch (e: Exception) {
            log.error("Email: ${userSessionBean.emailId} -> Error while creating the course", e)
            throw CourseException("Error while creating the course")
        }
    }

    /**
     * Get the course by creator
     */
    override fun getCourse(userSessionBean: UserSessionBean): ApiResp<*> {

        try {
            val emailId: String =
                userSessionBean.emailId ?: throw RuntimeException("Session expired, Please login again")

            log.info("Email: $emailId -> Getting the course")

            val user = userService.getUserInfoByEmail(emailId)

            val courseList = courseRepository.findByCreator(user)

            log.info("Email: $emailId -> Course list: $courseList")

            return ApiResp(
                status = HttpStatus.OK.value(),
                message = "Course list",
                data = courseList
            )
        } catch (e: Exception) {
            log.error("Email: ${userSessionBean.emailId} -> Error while getting the course", e)
            throw CourseException("Error while getting the course")
        }
    }

    /**
     * Get all the courses
     */
    override fun getAllCourse(userSessionBean: UserSessionBean, search: String): ApiResp<*> {

        try {
            val emailId: String =
                userSessionBean.emailId ?: throw RuntimeException("Session expired, Please login again")

            log.info("Email: $emailId -> Getting all By search: $search")

            val courseList = courseRepository.findAllWithSearch(search)

            log.info("Email: $emailId -> All course list: $courseList")

            return ApiResp(
                status = HttpStatus.OK.value(),
                message = "All course list",
                data = courseList
            )
        } catch (e: Exception) {
            log.error("Email: ${userSessionBean.emailId} -> Error while getting all course", e)
            throw CourseException("Error while getting all course")
        }

    }

    /**
     * Purchase the course
     */
    override fun purchaseCourse(courseId: Long, userSessionBean: UserSessionBean): ApiResp<*> {
        try {
            val emailId: String =
                userSessionBean.emailId ?: throw RuntimeException("Session expired, Please login again")

            log.info("Email: $emailId -> Purchasing the course: $courseId")

            val user = userService.getUserInfoByEmail(emailId)

            val course = courseRepository.findById(courseId)
                .orElseThrow { CourseException("Course not found with courseId: $courseId") }

            var purchase = Purchase(
                course = course,
                user = user
            )

            purchase = purchaseRepository.save(purchase)

            log.info("Email: $emailId -> Course purchased successfully: $course")

            return ApiResp(
                status = HttpStatus.CREATED.value(),
                message = "Course purchased successfully",
                data = purchase
            )
        } catch (e: Exception) {
            log.error("Email: ${userSessionBean.emailId} -> Error while purchasing the course", e)
            throw CourseException("Error while purchasing the course")
        }
    }

    override fun getPurchaseCourse(userSessionBean: UserSessionBean): ApiResp<*> {
        try {
            val emailId: String =
                userSessionBean.emailId ?: throw RuntimeException("Session expired, Please login again")

            log.info("Email: $emailId -> Getting the purchased course")

            val user = userService.getUserInfoByEmail(emailId)

            val courseList = purchaseRepository.findCoursesByUser(user)

            log.info("Email: $emailId -> Purchased course list: $courseList")

            return ApiResp(
                status = HttpStatus.OK.value(),
                message = "Purchased course list",
                data = courseList
            )
        } catch (e: Exception) {
            log.error("Email: ${userSessionBean.emailId} -> Error while getting the purchased course", e)
            throw CourseException("Error while getting the purchased course")
        }
    }
}