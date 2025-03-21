package com.tori.mementocaps.presentation.repository

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.UserCapsule
import com.tori.mementocaps.domain.repository.CapsuleRepository
import com.tori.mementocaps.domain.repository.UserCapsuleRepository
import com.tori.mementocaps.domain.repository.UserRepository
import com.tori.mementocaps.presentation.exception.MementoCapsBadRequestException
import com.tori.mementocaps.presentation.exception.MementoCapsForbiddenException
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
            .orElseThrow { MementoCapsBadRequestException("작성자 정보를 찾을 수 없습니다.") }

        val capsule = capsuleRepository.save(
            Capsule(title, content, openDate)
        )

        userCapsuleRepository.save(
            UserCapsule(user, capsule, Role.OWNER)
        )

        return capsule
    }

    fun deleteCapsule(
        capsuleId: Long,
        requestUserId: Long
    ): Unit {
        val requestUserRole = userCapsuleRepository.findUserRoleInCapsule(capsuleId, requestUserId)
            .orElseThrow { MementoCapsForbiddenException("참여 정보가 없습니다.") }
        if (requestUserRole != Role.OWNER) {
            throw MementoCapsForbiddenException("삭제 권한이 없습니다.")
        }

        userCapsuleRepository.deleteAllByCapsuleId(capsuleId)
        capsuleRepository.deleteById(capsuleId)
    }
}