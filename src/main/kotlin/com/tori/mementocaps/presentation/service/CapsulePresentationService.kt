package com.tori.mementocaps.presentation.service

import com.tori.mementocaps.presentation.dto.CapsuleDTO
import com.tori.mementocaps.presentation.dto.CapsuleListDTO
import com.tori.mementocaps.presentation.exception.MementoCapsInternalServerErrorException
import com.tori.mementocaps.presentation.repository.CapsulePresentationRepository
import com.tori.mementocaps.presentation.requestDTO.CapsuleRequestDTO
import com.tori.mementocaps.presentation.requestDTO.RequestUserDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class CapsulePresentationService(
    private val capsuleRepository: CapsulePresentationRepository,
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

    // NOTE: 캡슐 저장
    @Transactional
    fun createCapsule(request: CapsuleRequestDTO): Long {
        val capsuleResult = capsuleRepository.createCapsuleWithOwner(
            title = request.title,
            content = request.content,
            openDate = request.openDate,
            writerId = request.wirterId
        )
        return requireNotNull(capsuleResult.id) { throw MementoCapsInternalServerErrorException("캡슐 저장 후 캡슐 id를 얻지 못했습니다.") }
    }

    // NOTE: 캡슐 삭제
    @Transactional
    fun deleteCapsule(request: RequestUserDTO, capsuleId: Long) {
        capsuleRepository.deleteCapsule(capsuleId = capsuleId, requestUserId = request.requestUserId)
    }
}