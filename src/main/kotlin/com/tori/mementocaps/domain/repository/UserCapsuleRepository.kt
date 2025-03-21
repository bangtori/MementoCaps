package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.User
import com.tori.mementocaps.domain.entity.UserCapsule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

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

    @Query("SELECT uc.capsule FROM UserCapsule uc WHERE uc.user.id = :userId")
    fun findAllCapsulesByUserId(@Param("userId") userId: Long): List<Capsule>

    // 유저 롤 반환
    @Query("SELECT uc.role FROM UserCapsule uc WHERE uc.capsule.id = :capsuleId AND uc.user.id = :userId")
    fun findUserRoleInCapsule(
        @Param("capsuleId") capsuleId: Long,
        @Param("userId") userId: Long
    ): Optional<Role>

    // 유저가 존제하는지 아닌지

    @Query(
        """
        SELECT CASE WHEN COUNT(uc) > 0 THEN true ELSE false END 
        FROM UserCapsule uc 
        WHERE uc.user.id = :userId AND uc.capsule.id = :capsuleId
        """
    )
    fun existsByUserIdAndCapsuleId(
        @Param("userId") userId: Long,
        @Param("capsuleId") capsuleId: Long
    ): Boolean

    // 삭제
    fun deleteAllByCapsuleId(capsuleId: Long)
}