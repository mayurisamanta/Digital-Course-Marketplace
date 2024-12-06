package com.kotlin.digital.course.dto


/**
 * ApiResp for handling API response
 */
data class ApiResp<T>(
    val status: Int? = null,

    val message: String? = null,

    val error: String? = null,

    val data: T? = null
)