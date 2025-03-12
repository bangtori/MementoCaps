package com.tori.mementocaps.domain.entity

import com.tori.mementocaps.domain.constant.Role
import jakarta.persistence.*

@Entity
class UserCapsule(
    user: User,
    capsule: Capsule,
    role: Role
): BaseEntity() {
    @Id // PK 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_capsule_id")
    var id: Long? = null

    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user

    @ManyToOne(targetEntity = Capsule::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_id", nullable = false)
    var capsule: Capsule = capsule


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = role
}