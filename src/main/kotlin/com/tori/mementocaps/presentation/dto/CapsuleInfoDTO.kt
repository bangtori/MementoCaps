package com.tori.mementocaps.presentation.dto

import com.tori.mementocaps.domain.entity.Capsule
import java.time.format.DateTimeFormatter

class CapsuleInfoDTO(
    val capsuleId: Long?,
    val title: String,
    val content: String,
    val openDate: String,
) {
    constructor(capsule: Capsule): this(
        capsuleId = capsule.id,
        title = capsule.title,
        content = capsule.content,
        openDate = capsule.openDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 EEEE"))
    )
}