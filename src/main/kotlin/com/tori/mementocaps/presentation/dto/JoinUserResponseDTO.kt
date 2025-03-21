package com.tori.mementocaps.presentation.dto

import com.tori.mementocaps.domain.entity.Capsule

data class JoinUserResponseDTO(
    val capsuleInfo: CapsuleInfoDTO,
    val role: String
)
