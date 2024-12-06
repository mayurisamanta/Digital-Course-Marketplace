package com.kotlin.digital.course.controller

import com.kotlin.digital.course.dto.ApiResp
import com.kotlin.digital.course.dto.UserSessionBean
import com.kotlin.digital.course.service.UserService
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * UserController
 */
@RestController
@Slf4j
class UserController (
    val userService: UserService
){

    @GetMapping("/admin/user")
    fun getUsersByRoles(authentication: Authentication): ResponseEntity<ApiResp<*>> {
        val userSessionBean = authentication.details as UserSessionBean
        log.info("Email: ${userSessionBean.emailId} -> Getting all users")
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(userSessionBean))

    }
}