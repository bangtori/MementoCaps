package com.tori.mementocaps.presentation.requestDTO

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignInRequestDTO(
    @field:NotBlank(message = "필수값입니다.")
    @field:Email(message = "이메일 형식에 맞춰야합니다.")
    val email: String,

    @field:NotBlank(message = "필수값입니다.")
    val password: String
)
