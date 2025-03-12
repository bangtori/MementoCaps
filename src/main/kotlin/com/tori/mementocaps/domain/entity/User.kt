package com.tori.mementocaps.domain.entity

import jakarta.persistence.*

@Entity
class User(
    nickName: String,
    email: String,
    password: String
): BaseEntity() {
    @Id // PK 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null

    @Column(nullable = false)
    var nickName: String = nickName

    @Column(nullable = false, unique = true)
    var email: String = email

    @Column(nullable = false)
    var password: String = password

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var capsules: MutableList<UserCapsule> = mutableListOf()
}