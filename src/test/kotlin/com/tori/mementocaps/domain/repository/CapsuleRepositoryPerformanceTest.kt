package com.tori.mementocaps.domain.repository

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.User
import com.tori.mementocaps.domain.entity.UserCapsule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate
import kotlin.system.measureNanoTime
import kotlin.test.Test


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CapsuleRepositoryPerformanceTest(
    @Autowired val capsuleRepository: CapsuleRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val userCapsuleRepository: UserCapsuleRepository
) {
    private lateinit var testUser: User

    @BeforeAll
    fun setup() {
        println("----- 데이터 초기화 시작 -----")

        testUser = userRepository.save(User(nickName = "PerformanceUser", email = "perf@example.com", password = "1234"))

        val capsules = mutableListOf<Capsule>()
        val userCapsules = mutableListOf<UserCapsule>()

        for (i in 1..100) { // 테스트 데이터를 1000개 생성
            val capsule = capsuleRepository.save(Capsule(title = "Capsule $i", content = "Content $i", openDate = LocalDate.now(), writer = testUser))
            capsules.add(capsule)
        }
        userCapsuleRepository.saveAll(userCapsules)

        println("----- 데이터 초기화 완료 -----")
    }

    @Test
    @DisplayName("CapsuleRepository: userId로 캡슐 조회 성능 테스트")
    fun testCapsuleRepositoryPerformance() {
        val executionTime = measureNanoTime {
            val capsules = capsuleRepository.findAllByUserId(testUser.id!!)
            println("Capsules found: ${capsules.size}")
        }
        println("CapsuleRepository Execution Time: $executionTime ns")
    }

    @Test
    @DisplayName("UserCapsuleRepository: userId로 캡슐 조회 성능 테스트")
    fun testUserCapsuleRepositoryPerformance() {
        val executionTime = measureNanoTime {
            val capsules = userCapsuleRepository.findAllCapsulesByUserId(testUser.id!!)
            println("Capsules found: ${capsules.size}")
        }
        println("UserCapsuleRepository Execution Time: $executionTime ns")
    }
}