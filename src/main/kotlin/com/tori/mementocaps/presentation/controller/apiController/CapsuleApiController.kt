package com.tori.mementocaps.presentation.controller.apiController

import com.tori.mementocaps.presentation.dto.CapsuleDTO
import com.tori.mementocaps.presentation.dto.CapsuleListDTO
import com.tori.mementocaps.presentation.exception.CapsuleNotFoundException
import com.tori.mementocaps.presentation.service.CapsulePresentationService
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
            .orElseThrow { CapsuleNotFoundException() }
    }
}