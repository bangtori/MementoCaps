package com.tori.mementocaps.presentation.repository

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.UserCapsule
import com.tori.mementocaps.domain.repository.CapsuleRepository
import com.tori.mementocaps.domain.repository.UserCapsuleRepository
import com.tori.mementocaps.domain.repository.UserRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class CapsulePresentationRepository(
    private val userCapsuleRepository: UserCapsuleRepository,
    private val userRepository: UserRepository,
    private val capsuleRepository: CapsuleRepository
) {
    fun getCapsuleList(userId: Long): List<UserCapsule> {
        return userCapsuleRepository.findUserCapsulesByUserId(userId)
    }

    fun getCapsuleDetail(capsuleId: Long): List<UserCapsule> {
        return userCapsuleRepository.findUserCapsulesByCapsuleId(capsuleId)
    }

    fun createCapsuleWithOwner(
        title: String,
        content: String,
        openDate: LocalDate,
        writerId: Long
    ): Capsule {
        val user = userRepository.findById(writerId)
            .orElseThrow { IllegalArgumentException("작성자 없음") }

        val capsule = capsuleRepository.save(
            Capsule(title, content, openDate)
        )

        userCapsuleRepository.save(
            UserCapsule(user, capsule, Role.OWNER)
        )

        return capsule
    }
}