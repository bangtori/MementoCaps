package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.entity.UserCapsule
import org.springframework.data.jpa.repository.JpaRepository

interface UserCapsuleRepository: JpaRepository<UserCapsule, Long> {
    fun findAllByUserId(userId: Long): List<UserCapsule>
    fun existsByUserId(userId: Long): Boolean
    fun findAllByCapsuleId(capsuleId: Long): List<UserCapsule>
}