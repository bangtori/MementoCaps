package com.tori.mementocaps.common.utils

import java.security.SecureRandom

object InviteCodeGenerator {
    private const val CODE_LENGTH = 8 // 초대 코드 길이 (6~10 추천)
    private val CHAR_POOL: List<Char> = ('A'..'Z') + ('0'..'9') // 대문자 + 숫자 조합
    private val random = SecureRandom()

    fun generateInviteCode(): String {
        return (1..CODE_LENGTH)
            .map { CHAR_POOL[random.nextInt(CHAR_POOL.size)] }
            .joinToString("")
    }
}