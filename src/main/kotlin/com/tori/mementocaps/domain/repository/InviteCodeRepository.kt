package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.entity.InviteCode
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface InviteCodeRepository: JpaRepository<InviteCode, Long> {
    fun findByCode(code: String): Optional<InviteCode>
    fun findAllByExpiresAtBefore(now: LocalDateTime): List<InviteCode>
    fun deleteAllByExpiresAtBefore(now: LocalDateTime)
}