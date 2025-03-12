package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.entity.User
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    // 이메일 중복 체크
    fun existsByEmail(email: String): Boolean

    fun findByEmailAndPassword(email: String, password: String): Optional<User>
}