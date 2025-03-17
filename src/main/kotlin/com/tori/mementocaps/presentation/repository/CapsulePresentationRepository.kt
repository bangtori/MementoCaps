package com.tori.mementocaps.presentation.repository

import com.tori.mementocaps.domain.entity.UserCapsule
import com.tori.mementocaps.domain.repository.UserCapsuleRepository
import org.springframework.stereotype.Repository

@Repository
class CapsulePresentationRepository(
    private val userCapsuleRepository: UserCapsuleRepository
) {
    fun getCapsuleList(userId: Long): List<UserCapsule> {
        return userCapsuleRepository.findUserCapsulesByUserId(userId)
    }

    fun getCapsuleDetail(capsuleId: Long): List<UserCapsule> {
        return userCapsuleRepository.findUserCapsulesByCapsuleId(capsuleId)
    }
}