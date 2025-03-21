package com.tori.mementocaps.presentation.requestDTO

import jakarta.validation.Valid
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CapsuleRequestDTO(
    @field:NotBlank(message = "필수값입니다.")
    val title: String,

    @field:NotBlank(message = "필수값입니다.")
    val content: String,

    @field:NotNull(message = "필수값입니다.")
    @field:Future(message = "오늘 이후의 값이어야합니다.")
    val openDate: LocalDate,

    @field:NotNull(message = "필수값입니다.")
    val wirterId: Long
)
