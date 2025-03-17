package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.entity.Capsule
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CapsuleRepository: JpaRepository<Capsule, Long> {
    override fun findById(id: Long): Optional<Capsule>
    override fun deleteById(id: Long)

    @Query("SELECT c FROM Capsule c JOIN UserCapsule uc ON c.id = uc.capsule.id WHERE uc.user.id = :userId")
    fun findAllByUserId(@Param("userId") userId: Long): List<Capsule>
}