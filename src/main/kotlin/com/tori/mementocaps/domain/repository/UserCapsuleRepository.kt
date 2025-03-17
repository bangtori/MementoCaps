package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.UserCapsule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserCapsuleRepository: JpaRepository<UserCapsule, Long> {
    // 특정 유저 아이디로 조회
    @Query("SELECT uc FROM UserCapsule uc JOIN FETCH uc.capsule WHERE uc.user.id = :userId")
    fun findUserCapsulesByUserId(@Param("userId") userId: Long): List<UserCapsule>

    // 캡슐 아이디로 조회
    @Query(
        """
    SELECT uc FROM UserCapsule uc 
    JOIN FETCH uc.capsule c 
    JOIN FETCH uc.user u 
    WHERE c.id = :capsuleId
    """
    )
    fun findUserCapsulesByCapsuleId(@Param("capsuleId") capsuleId: Long): List<UserCapsule>

    fun existsByUserId(userId: Long): Boolean

    @Query("SELECT uc.capsule FROM UserCapsule uc WHERE uc.user.id = :userId")
    fun findAllCapsulesByUserId(@Param("userId") userId: Long): List<Capsule>
}