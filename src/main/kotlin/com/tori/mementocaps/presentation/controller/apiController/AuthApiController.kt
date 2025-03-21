package com.tori.mementocaps.presentation.controller.apiController

import com.tori.mementocaps.presentation.dto.DefaultResponse
import com.tori.mementocaps.presentation.dto.SignInDTO
import com.tori.mementocaps.presentation.requestDTO.SignInRequestDTO
import com.tori.mementocaps.presentation.requestDTO.SignUpRequestDTO
import com.tori.mementocaps.presentation.service.AuthPresentationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthApiController(
    private val authPresentationService: AuthPresentationService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid userInfo: SignUpRequestDTO): DefaultResponse {
        val userId = authPresentationService.signUp(userInfo)
        return DefaultResponse(status = true)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody @Valid signInDTO: SignInRequestDTO ): SignInDTO {
        val userId = authPresentationService.signIn(request = signInDTO)
        return SignInDTO(userId = userId)
    }
}