package com.tori.mementocaps.presentation.requestDTO

data class JoinUserRequestDTO(
    val inviteCode: String,
    val joinUserId: Long
)