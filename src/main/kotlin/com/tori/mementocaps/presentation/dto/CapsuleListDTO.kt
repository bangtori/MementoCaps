package com.tori.mementocaps.presentation.dto

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import java.time.format.DateTimeFormatter

class CapsuleListDTO(
    val capsuleInfo: CapsuleInfoDTO,
    var role: String
) {
    constructor(capsule: Capsule, role: Role): this(
        capsuleInfo = CapsuleInfoDTO(
            capsuleId = capsule.id,
            title = capsule.title,
            content = "",
            openDate = capsule.openDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 EEEE")),
        ),
        role = role.toString()
    )
}