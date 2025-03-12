package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.entity.Capsule
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository

interface CapsuleRepository: JpaRepository<Capsule, Long> {
    override fun findById(id: Long): Optional<Capsule>
    override fun deleteById(id: Long)
}