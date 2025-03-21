package com.tori.mementocaps.presentation.service

import com.tori.mementocaps.presentation.exception.MementoCapsInternalServerErrorException
import com.tori.mementocaps.presentation.repository.AuthPresentationRepository
import com.tori.mementocaps.presentation.requestDTO.SignInRequestDTO
import com.tori.mementocaps.presentation.requestDTO.SignUpRequestDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthPresentationService(
    private val authPresentationRepository: AuthPresentationRepository
) {
    @Transactional
    fun signUp(
        request: SignUpRequestDTO
    ): Long {
        val user = authPresentationRepository.signUp(
            nickName = request.nickname,
            email = request.email,
            password = request.password
        )
        return requireNotNull(user.id) {
            MementoCapsInternalServerErrorException("회원가입 후 유저 id를 얻지못했습니다.")
        }
    }

    @Transactional
    fun signIn(
        request: SignInRequestDTO
    ): Long {
        val user = authPresentationRepository.signIn(
            email = request.email,
            password = request.password
        )

        return requireNotNull(user.id) {
            MementoCapsInternalServerErrorException("로그인 유저 정보의 id를 얻지못했습니다.")
        }
    }
}