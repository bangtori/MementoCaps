package com.tori.mementocaps.domain.entity

import com.tori.mementocaps.domain.constant.Role
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Capsule(
    title: String,
    content: String,
    openDate: LocalDate,
    writer: User
): BaseEntity() {
    @Id // PK 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capsule_id")
    var id: Long? = null

    var title: String = title
    var content: String = content
    var openDate: LocalDate = openDate

    @OneToMany(mappedBy = "capsule", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var users: MutableList<UserCapsule> = mutableListOf(
        UserCapsule(
            user = writer,
            capsule = this,
            role = Role.OWNER
        )
    )

    @OneToMany(mappedBy = "capsule", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var codes: MutableList<InviteCode> = mutableListOf()

    fun addUsers(user: User) {
        this.users.add(UserCapsule(
            user = user,
            capsule = this,
            role = Role.MEMBER
        ))
    }

    fun addCode() {
        this.codes.add(InviteCode(capsule = this))
    }
}