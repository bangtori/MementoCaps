package com.tori.mementocaps.domain

import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.InviteCode
import com.tori.mementocaps.domain.entity.User
import com.tori.mementocaps.domain.entity.UserCapsule
import com.tori.mementocaps.domain.repository.CapsuleRepository
import com.tori.mementocaps.domain.repository.InviteCodeRepository
import com.tori.mementocaps.domain.repository.UserCapsuleRepository
import com.tori.mementocaps.domain.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Profile(value = ["default"])
class DataInitializer(
    private val userRepository: UserRepository,
    private val capsuleRepository: CapsuleRepository,
    private val userCapsuleRepository: UserCapsuleRepository,
    private val inviteCodeRepository: InviteCodeRepository
) {
    val log = LoggerFactory.getLogger(DataInitializer::class.java)

    @PostConstruct // 객체가 생성된 후 초기화 작업을 수행하는 메서드
    fun initializeData() {
        log.info("스프링이 실행되었습니다. 테스트 데이터를 초기화합니다.")

        // User 초기화
        val users = mutableListOf<User>(
            User(
                nickName = "홍길동",
                email = "user1@email.com",
                password = "password123"
            ),
            User(
                nickName = "방유빈",
                email = "user2@email.com",
                password = "password123"
            ),
            User(
                nickName = "김스프링",
                email = "user3@email.com",
                password = "password123"
            ),
            User(
                nickName = "박멤버",
                email = "user4@email.com",
                password = "password123"
            )
        )

        userRepository.saveAll(users)

        // Capsule 초기화
        val capsules = mutableListOf<Capsule>(
            Capsule(
                title = "안녕하쇼",
                content = "오래보자 친구들아~",
                openDate = LocalDate.of(2025, 12, 31),
            ),
            Capsule(
                title = "5년 뒤 나에게",
                content = "아버지를 아버지라 부를 수 있기를 아디오스",
                openDate = LocalDate.of(2030, 1, 1),
            ),
            Capsule(
                title = "행운의 편지",
                content = """
                    이 편지는 영국에서 최초로 시작되어 
                    어쩌구 저쩌구 7통 써서 전달하셈
                """.trimIndent(),
                openDate = LocalDate.of(2025, 12, 31),
            ),
            Capsule(
                title = "약속",
                content = "비밀이야 ㅋ",
                openDate = LocalDate.of(2025, 12, 31),
            ),
            Capsule(
                title = "웩 누구니?",
                content = "사랑해~ 웩 누구니?",
                openDate = LocalDate.of(2025, 12, 31),
            ),
            Capsule(
                title = "3년 뒤에는 ",
                content = "멋진 개발자가 되어있겠지? 그럴거라 믿는다.",
                openDate = LocalDate.of(2028, 1, 1),
            ),
        )
        capsuleRepository.saveAll(capsules)

        val wirterMap = listOf(0, 0, 1, 1, 2, 2)
        // 유저 캡슐 관계 저장
        for (i in 0..<capsules.count()) {
            val userCapsules = mutableListOf<UserCapsule>(
                UserCapsule(
                    user = users[wirterMap[i]],
                    capsule = capsules[i],
                    role = Role.OWNER
                ),
                UserCapsule(
                    user = users[3],
                    capsule = capsules[i],
                    role = Role.MEMBER
                )
            )
            userCapsuleRepository.saveAll(userCapsules)
            if (i % 2 == 0) {
                val codes = mutableListOf<InviteCode>(
                    InviteCode(
                        capsule = capsules[i],
                        code = null
                    ),
                    InviteCode(
                        capsule = capsules[i],
                        code = null
                    )
                )
                inviteCodeRepository.saveAll(codes)
            }
        }
    }
}