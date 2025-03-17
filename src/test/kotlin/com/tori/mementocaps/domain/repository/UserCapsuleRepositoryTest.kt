package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.User
import com.tori.mementocaps.domain.entity.UserCapsule
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import kotlin.test.Test


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserCapsuleRepositoryTest(
    @Autowired val capsuleRepository: CapsuleRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val userCapsuleRepository: UserCapsuleRepository
) {
    val DATA_SIZE = 9
    private lateinit var testUsers: List<User>
    private lateinit var testCapsules: List<Capsule>
    // 캡슐 생성
    private fun createCapsule(n: Int): Capsule {
        return Capsule(
            title = "${n}",
            content = "Content {n}",
            openDate = LocalDate.of(2025, 12, 31)
        )
    }
    @BeforeAll
    @DisplayName("📌 테스트 데이터 초기화")
    fun beforeAll() {
        println("===== 테스트 데이터 초기화 =====")
        // 유저 생성
        testUsers = listOf(
            User(nickName = "User1", email = "user1@example.com", password = "password1"),
            User(nickName = "User2", email = "user2@example.com", password = "password2"),
            User(nickName = "User3", email = "user3@example.com", password = "password3")
        )
        userRepository.saveAll(testUsers)
        // 캡슐 생성
        testCapsules = capsuleRepository.saveAll(
            List(DATA_SIZE) { i -> Capsule(title = "Capsule ${i + 1}", content = "Content ${i + 1}", openDate = LocalDate.of(2025, 12, 31)) }
        )
        // UserCapsule 생성
        val userCapsules = mutableListOf<UserCapsule>()
        for (capsule in testCapsules) {
            val ownerIndex = (capsule.id!! % 3).toInt() // 캡슐 ID % 3 값으로 OWNER 유저 인덱스 결정
            val owner = testUsers[ownerIndex] // OWNER 유저 선택
            userCapsules.add(UserCapsule(user = owner, capsule = capsule, role = Role.OWNER))
        }
        userCapsuleRepository.saveAll(userCapsules)
        println("===== 테스트 데이터 초기화 완료 =====")
    }

    @Test
    @DisplayName("🧑‍💻 특정 유저의 캡슐 목록 조회 테스트")
    fun findUserCapsulesByUserId_shouldReturnCorrectUserCapsules() {
        println("===== findUserCapsulesByUserId 테스트 시작 =====")

        for (user in testUsers) {
            val userCapsules = userCapsuleRepository.findUserCapsulesByUserId(user.id!!)
            Assertions.assertThat(userCapsules).hasSize(3)
            Assertions.assertThat(userCapsules.all { it.user.id == user.id }).isTrue

            println("✅ User ${user.nickName} has ${userCapsules.size} capsules")
        }

        println("===== findUserCapsulesByUserId 테스트 완료 =====")
    }

    @Test
    @DisplayName("📦 특정 캡슐의 참여자 목록 조회 테스트")
    fun findUserCapsulesByCapsuleId_shouldReturnCorrectUserCapsules() {
        println("===== findUserCapsulesByCapsuleId 테스트 시작 =====")

        for (capsule in testCapsules) {
            val capsuleUsers = userCapsuleRepository.findUserCapsulesByCapsuleId(capsule.id!!)
            Assertions.assertThat(capsuleUsers).hasSize(1)
            Assertions.assertThat(capsuleUsers.all { it.capsule.id == capsule.id }).isTrue
            println("✅ Capsule ${capsule.title} has ${capsuleUsers.size} users")
        }

        println("===== findUserCapsulesByCapsuleId 테스트 완료 =====")
    }
}