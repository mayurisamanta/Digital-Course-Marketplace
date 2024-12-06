package com.kotlin.digital.course.repository

import com.kotlin.digital.course.entity.Purchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository: JpaRepository<Purchase, Long> 