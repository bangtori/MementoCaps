package com.tori.mementocaps.domain.entity

import com.tori.mementocaps.common.utils.InviteCodeGenerator
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class InviteCode(
    capsule: Capsule,
    code: String?
): BaseEntity() {
    @Id // PK 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    var id: Long? = null

    @ManyToOne(targetEntity = Capsule::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_id", nullable = false)
    var capsule: Capsule = capsule

    // 초대 코드 생성
    @Column(unique = true, nullable = false)
    var code: String = code ?: InviteCodeGenerator.generateInviteCode()

    @Column(nullable = false)
    var expiresAt: LocalDateTime = LocalDateTime.now().plusDays(7)

}