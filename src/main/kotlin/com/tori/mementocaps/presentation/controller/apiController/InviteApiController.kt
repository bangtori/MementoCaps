package com.tori.mementocaps.presentation.controller.apiController

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.presentation.dto.CapsuleInfoDTO
import com.tori.mementocaps.presentation.dto.InviteCodeDTO
import com.tori.mementocaps.presentation.dto.JoinUserResponseDTO
import com.tori.mementocaps.presentation.requestDTO.RequestUserDTO
import com.tori.mementocaps.presentation.requestDTO.JoinUserRequestDTO
import com.tori.mementocaps.presentation.service.InviteCodePresentationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/capsules")
class InviteApiController(
    private val inviteCodePresentationService: InviteCodePresentationService
) {
    @PostMapping("/{capsuleId}/invite")
    fun createInviteCode(
        @PathVariable capsuleId: Long,
        @RequestBody userInfo: RequestUserDTO
    ): InviteCodeDTO {
        val code = inviteCodePresentationService.createInviteCode(
            requestUserId = userInfo.requestUserId,
            capsuleId = capsuleId
        )

        return InviteCodeDTO(code)
    }

    @PostMapping("/join")
    fun joinUser(
        @RequestBody request: JoinUserRequestDTO
    ): JoinUserResponseDTO {
        val capsule = inviteCodePresentationService.joinCodeUser(
            inviteCode = request.inviteCode,
            joinUserId = request.joinUserId
        )

        return JoinUserResponseDTO(
            capsuleInfo = CapsuleInfoDTO(capsule),
            role = Role.MEMBER.toString()
        )
    }
}