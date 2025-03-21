package com.tori.mementocaps.presentation.controller.apiController

import com.tori.mementocaps.presentation.dto.CapsuleDTO
import com.tori.mementocaps.presentation.dto.CapsuleListDTO
import com.tori.mementocaps.presentation.dto.CreateCapsuleDTO
import com.tori.mementocaps.presentation.dto.DefaultResponse
import com.tori.mementocaps.presentation.exception.MementoCapsNotFoundException
import com.tori.mementocaps.presentation.requestDTO.CapsuleRequestDTO
import com.tori.mementocaps.presentation.requestDTO.RequestUserDTO
import com.tori.mementocaps.presentation.service.CapsulePresentationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/capsules")
class CapsuleApiController(
    private val capsulePresentationService: CapsulePresentationService
) {
    @GetMapping("/list/{userId}")
    fun getList(@PathVariable userId: Long): List<CapsuleListDTO> {
        return capsulePresentationService.getCapsuleList(userId)
    }

    @GetMapping("/{capsuleId}")
    fun getCapsule(
        @PathVariable capsuleId: Long,
        @RequestHeader("userId") userId: Long
    ): CapsuleDTO {
        return capsulePresentationService.getCapsuleDetail(capsuleId = capsuleId, userId = userId)
            .orElseThrow { MementoCapsNotFoundException("capsuleId를 찾을 수 없습니다.") }
    }

    @PostMapping
    fun createCapsule(
        @RequestBody @Valid request: CapsuleRequestDTO
    ): CreateCapsuleDTO {
        val capsuleId = capsulePresentationService.createCapsule(request = request)
        return CreateCapsuleDTO(capsuleId = capsuleId)
    }


    @DeleteMapping("/{capsuleId}")
    fun deleteCapsule(
        @PathVariable capsuleId: Long,
        @RequestBody request: RequestUserDTO
    ): DefaultResponse {
        capsulePresentationService.deleteCapsule(request, capsuleId)
        return DefaultResponse(true)
    }
}