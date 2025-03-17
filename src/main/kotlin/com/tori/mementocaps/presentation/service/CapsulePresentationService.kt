package com.tori.mementocaps.presentation.service

import com.tori.mementocaps.presentation.dto.CapsuleDTO
import com.tori.mementocaps.presentation.dto.CapsuleListDTO
import com.tori.mementocaps.presentation.repository.CapsulePresentationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class CapsulePresentationService(
    private val capsuleRepository: CapsulePresentationRepository
) {
    @Transactional(readOnly = true)
    fun getCapsuleList(userId: Long): List<CapsuleListDTO> {
        val userCapsules = capsuleRepository.getCapsuleList(userId)
        return userCapsules.map { userCapsule ->
            CapsuleListDTO(
                capsule = userCapsule.capsule,
                role = userCapsule.role
            )
        }
    }

    @Transactional(readOnly = true)
    fun getCapsuleDetail(capsuleId: Long, userId: Long): Optional<CapsuleDTO> {
        val userCapsules = capsuleRepository.getCapsuleDetail(capsuleId)
        if (userCapsules.isEmpty()) {
            return Optional.empty()
        }
        return Optional.of(CapsuleDTO(userCapsules = userCapsules, userId = userId))
    }
}