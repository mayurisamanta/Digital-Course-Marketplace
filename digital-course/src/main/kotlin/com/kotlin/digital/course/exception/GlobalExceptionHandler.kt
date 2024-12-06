package com.kotlin.digital.course.exception

import com.kotlin.digital.course.dto.ApiResp
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * GlobalExceptionHandler
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Handle UserException
     *
     * @param userException
     */
    @ExceptionHandler(UserException::class)
    fun handleUserException(userException: UserException): ApiResp<String> {
        return ApiResp(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "User Related Error",
            message = userException.message
        )
    }

    /**
     * Handle CourseException
     *
     * @param courseException
     */
    @ExceptionHandler(CourseException::class)
    fun handleCourseException(courseException: CourseException): ApiResp<String> {
        return ApiResp(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Course Related Error",
            message = courseException.message
        )
    }

    /**
     * Handle MethodArgumentNotValidException
     *
     * @param methodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        methodArgumentNotValidException: MethodArgumentNotValidException
    ): ApiResp<Map<String, String>> {
        val errorMap = methodArgumentNotValidException.bindingResult.allErrors.associate { error ->
            val fieldName = (error as FieldError).field
            val defaultMessage = error.defaultMessage ?: "Invalid value"
            fieldName to defaultMessage
        }

        return ApiResp(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Validation Error",
            message = "Validation failed for one or more fields.",
            data = errorMap
        )
    }

    /**
     * Handle generic Exception
     *
     * @param exception
     */
    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ApiResp<String> {
        return ApiResp(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = exception.message
        )
    }
}