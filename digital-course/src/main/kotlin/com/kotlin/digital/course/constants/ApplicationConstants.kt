package com.kotlin.digital.course.constants

/**
 * Constants used in the application
 */
object ApplicationConstants {

    const val JWT_SECRET_KEY = "JWT_SECRET"

    const val JWT_SECRET_DEFAULT_VALUE = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4"

    const val JWT_HEADER = "Authorization"

    const val EMAIL_REGEX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"

    // Password must contain at least one digit [0-9].
    // Password must contain at least one lowercase Latin character [a-z].
    // Password must contain at least one uppercase Latin character [A-Z].
    // Password must contain at least one special character like @, $, #, % etc.
    // Password must not contain any whitespace.
    // Password must be at least 8 characters long.
    const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
}