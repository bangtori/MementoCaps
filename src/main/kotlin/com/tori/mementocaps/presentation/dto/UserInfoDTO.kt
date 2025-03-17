package com.tori.mementocaps.presentation.dto

import com.tori.mementocaps.domain.entity.User

class UserInfoDTO(
    val id: Long?,
    val nickName: String
) {
    constructor(user: User): this(
        id = user.id,
        nickName = user.nickName
    )
}