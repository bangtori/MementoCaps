package com.tori.mementocaps.presentation.repository

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.InviteCode
import com.tori.mementocaps.domain.entity.UserCapsule
import com.tori.mementocaps.domain.repository.CapsuleRepository
import com.tori.mementocaps.domain.repository.InviteCodeRepository
import com.tori.mementocaps.domain.repository.UserCapsuleRepository
import com.tori.mementocaps.domain.repository.UserRepository
import com.tori.mementocaps.presentation.exception.MementoCapsBadRequestException
import com.tori.mementocaps.presentation.exception.MementoCapsForbiddenException
import com.tori.mementocaps.presentation.exception.MementoCapsInternalServerErrorException
import com.tori.mementocaps.presentation.exception.MementoCapsNotFoundException
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InvitePresentationRepository(
    private val userCapsuleRepository: UserCapsuleRepository,
    private val inviteCodeRepository: InviteCodeRepository,
    private val capsuleRepository: CapsuleRepository,
    private val userRepository: UserRepository
) {
    fun createInviteCode(
        requestUserId: Long,
        capsuleId: Long
    ): InviteCode {
        val requestUserRole = userCapsuleRepository.findUserRoleInCapsule(capsuleId, requestUserId)
            .orElseThrow { MementoCapsForbiddenException("참여 정보가 없습니다.") }
        if (requestUserRole != Role.OWNER) {
            throw MementoCapsForbiddenException("초대 코드 생성 권한이 없습니다.")
        }

        val capsule = capsuleRepository.findById(capsuleId)
            .orElseThrow { MementoCapsNotFoundException("capsuleId를 찾을 수 없습니다.") }

        val code = InviteCode(capsule = capsule)
        inviteCodeRepository.save(code)

        return code
    }

    fun joinUser(inviteCode: String, joinUserId: Long): Capsule {
        val inviteCodeInfo = inviteCodeRepository.findByCode(inviteCode)
            .orElseThrow { MementoCapsBadRequestException("존재하지않는 초대코드 입니다.") }
        val capsuleId = inviteCodeInfo.capsule.id ?: throw MementoCapsInternalServerErrorException("캡슐 id를 찾을 수 없습니다.")
        val userInfo = userRepository.findById(joinUserId)
            .orElseThrow { MementoCapsBadRequestException("유저 정보를 찾을 수 없습니다.") }

        if (userCapsuleRepository.existsByUserIdAndCapsuleId(joinUserId, capsuleId)) {
            throw MementoCapsBadRequestException("이미 참여한 캡슐입니다.")
        }

        if (inviteCodeInfo.expiresAt.isBefore(LocalDateTime.now())) {
            throw MementoCapsBadRequestException("초대코드가 만료되었습니다.")
        }

        val userCapsule = UserCapsule(
            user = userInfo,
            capsule = inviteCodeInfo.capsule,
            role = Role.MEMBER
        )

        userCapsuleRepository.save(userCapsule)

        return inviteCodeInfo.capsule
    }
}