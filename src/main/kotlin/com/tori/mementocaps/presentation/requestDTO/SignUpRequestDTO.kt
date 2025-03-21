package com.tori.mementocaps.presentation.requestDTO

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequestDTO(
    @field:NotBlank(message = "필수값입니다.")
    @field:Email(message = "이메일 형식에 맞춰야합니다.")
    val email: String,

    @field:NotBlank(message = "필수값입니다.")
    @field:Size(min = 2, max = 10, message = "닉네임은 2글자에서 10글자 사이만 가능합니다.")
    val nickname: String,

    @field:NotBlank(message = "필수값입니다.")
    val password: String
)
