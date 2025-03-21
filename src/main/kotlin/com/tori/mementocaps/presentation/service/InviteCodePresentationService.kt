package com.tori.mementocaps.presentation.service

import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.InviteCode
import com.tori.mementocaps.presentation.repository.InvitePresentationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InviteCodePresentationService(
    private val invitePresentationRepository: InvitePresentationRepository
) {
    @Transactional
    fun createInviteCode(requestUserId: Long, capsuleId: Long): InviteCode {
        val inviteCode = invitePresentationRepository.createInviteCode(
            requestUserId = requestUserId,
            capsuleId = capsuleId
        )

        return inviteCode
    }

    @Transactional
    fun joinCodeUser(inviteCode: String, joinUserId: Long): Capsule {
        val capsule = invitePresentationRepository.joinUser(
            inviteCode = inviteCode,
            joinUserId = joinUserId
        )

        return capsule
    }
}