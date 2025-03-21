package com.tori.mementocaps.presentation.dto

import com.tori.mementocaps.domain.entity.InviteCode
import jakarta.annotation.PostConstruct
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InviteCodeDTO(
    val code: String,
    val expiresAt: LocalDateTime
) {
    constructor(inviteCode: InviteCode): this(
        code = inviteCode.code,
        expiresAt = inviteCode.expiresAt
    )
}