package com.kotlin.digital.course.controller

import com.kotlin.digital.course.constants.ApplicationConstants
import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.UserReq
import com.kotlin.digital.course.service.JwtService
import com.kotlin.digital.course.service.UserService
import jakarta.validation.Valid
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * LoginController
 */
@RestController
@Slf4j
class LoginController(
    private val jwtService: JwtService,
    private val userService: UserService
) {

    /**
     * login
     *
     * @param userReq
     */
    @PostMapping("/login")
    fun login(@RequestBody userReq: UserReq): ResponseEntity<ApiResp<*>> {
        log.info("Email: $userReq.emailId -> Logging in the user")

        return ResponseEntity.status(HttpStatus.OK)
            .header(ApplicationConstants.JWT_HEADER, jwtService.generateToken(userReq))
            .body(ApiResp(status = HttpStatus.OK.value(), message = "Login Successful", data = null))
    }

    /**
     * Register a new user
     *
     * @param userReq
     */
    @PostMapping("/register")
    fun register(@Valid @RequestBody userReq: UserReq): ResponseEntity<ApiResp<*>> {
        log.info("Email: ${userReq.emailId} -> Registering the user")
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userReq))
    }

    //api to test the jwt token
    @PostMapping("/test")
    fun test(): ResponseEntity<ApiResp<*>> {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResp(status = HttpStatus.OK.value(), message = "Test Successful", data = null))
    }
}