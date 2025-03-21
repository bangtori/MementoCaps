package com.tori.mementocaps.presentation.repository

import com.tori.mementocaps.domain.entity.User
import com.tori.mementocaps.domain.repository.UserRepository
import com.tori.mementocaps.presentation.exception.MementoCapsNotFoundException
import org.springframework.stereotype.Repository

@Repository
class AuthPresentationRepository(
    private val userRepository: UserRepository
) {
    fun signUp(
        nickName: String,
        email: String,
        password: String
    ): User {
        val user = User(
            nickName = nickName,
            email = email,
            password = password
        )
        userRepository.save(user)
        return user
    }

    fun signIn(
        email: String,
        password: String
    ): User {
        val user = userRepository.findByEmailAndPassword(
            email = email,
            password = password
        ).orElseThrow { MementoCapsNotFoundException("존재하지 않는 계정정보 입니다.") }

        return user
    }
}