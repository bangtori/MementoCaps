package com.tori.mementocaps.presentation.dto

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.UserCapsule

class CapsuleDTO(
    val role: String,
    val capsuleInfo: CapsuleInfoDTO,
    val writerInfo: UserInfoDTO,
    val participantInfo: List<UserInfoDTO>
) {
    constructor(userCapsules: List<UserCapsule>, userId: Long): this(
        role = userCapsules.find { it.user.id == userId }?.role?.toString()
            ?: throw IllegalArgumentException("userId에 해당하는 역할을 찾을 수 없습니다."),
        capsuleInfo = CapsuleInfoDTO(userCapsules.first().capsule),
        writerInfo = userCapsules
            .find { it.role == Role.OWNER }
            ?.let { UserInfoDTO(it.user) }
            ?: throw IllegalArgumentException("OWNER 역할을 가진 유저가 존재하지 않습니다."),
        participantInfo = userCapsules.map { UserInfoDTO(it.user) }
    ) {
        require(userCapsules.isNotEmpty()) { "userCapsules 리스트는 비어 있을 수 없습니다." }
    }
}