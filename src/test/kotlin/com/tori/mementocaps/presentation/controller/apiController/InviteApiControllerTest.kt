package com.tori.mementocaps.presentation.controller.apiController

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tori.mementocaps.domain.constant.Role
import com.tori.mementocaps.domain.entity.Capsule
import com.tori.mementocaps.domain.entity.InviteCode
import com.tori.mementocaps.domain.entity.User
import com.tori.mementocaps.domain.entity.UserCapsule
import com.tori.mementocaps.domain.repository.CapsuleRepository
import com.tori.mementocaps.domain.repository.InviteCodeRepository
import com.tori.mementocaps.domain.repository.UserCapsuleRepository
import com.tori.mementocaps.domain.repository.UserRepository
import com.tori.mementocaps.presentation.requestDTO.JoinUserRequestDTO
import com.tori.mementocaps.presentation.requestDTO.RequestUserDTO
import com.tori.mementocaps.presentation.requestDTO.SignUpRequestDTO
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[Auth API Controller Test]")
class InviteApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    private val userRepository: UserRepository,
    private val inviteCodeRepository: InviteCodeRepository,
    private val capsuleRepository: CapsuleRepository,
    private val userCapsuleRepository: UserCapsuleRepository
) {

    @BeforeEach
    fun setup() {
        val user1 = userRepository.save(User(email = "owner@test.com", nickName = "Owner", password = "pw"))
        val user2 = userRepository.save(User(email = "joiner@test.com", nickName = "Joiner", password = "pw"))
        val user3 = userRepository.save(User(email = "member@test.com", nickName = "Member", password = "pw"))

        val capsule = Capsule(title = "캡슐", content = "내용", openDate = LocalDate.now().plusDays(10))
        capsuleRepository.save(capsule)

        val userCapsule = UserCapsule(user = user1, capsule = capsule, role = Role.OWNER)
        userCapsuleRepository.save(userCapsule)

        val userCapsule2 = UserCapsule(user = user3, capsule = capsule, role = Role.MEMBER)
        userCapsuleRepository.save(userCapsule2)

        val inviteCode = InviteCode(capsule = capsule, code = "1Q8JOL07")
        inviteCodeRepository.save(inviteCode)
    }
    // NOTE: createInviteCode Test
    @Test
    @DisplayName("[createInviteCode] 정상적인 200 응답 반환 테스트")
    fun createInviteCode_Success() {
        // given
        val uri = "/1/invite"
        val body = RequestUserDTO(
            requestUserId = 1
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    @DisplayName("[createInviteCode] 403 에러 멤버 유저가 초대코드 생성")
    fun createInviteCode_Forbidden() {
        // given
        val uri = "/1/invite"
        val body = RequestUserDTO(
            requestUserId = 2
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    @DisplayName("[createInviteCode] 400 에러 캡슐아이디 찾을 수 없음")
    fun createInviteCode_BadRequest() {
        // given
        val uri = "/100/invite"
        val body = RequestUserDTO(
            requestUserId = 1
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    // NOTE: joinUser Test
    @Test
    @DisplayName("[joinUser] 정상적인 200 응답 반환 테스트")
    fun joinUser_Success() {
        // given
        val uri = "/join"
        val body = JoinUserRequestDTO(
            inviteCode = "1Q8JOL07",
            joinUserId = 2
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    @DisplayName("[joinUser] 400 응답 반환 테스트 - 존재하지않는 초대코드")
    fun joinUser_BadRequest_InviteCode() {
        // given
        val uri = "/join"
        val body = JoinUserRequestDTO(
            inviteCode = "code",
            joinUserId = 2
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    @DisplayName("[joinUser] 400 응답 반환 테스트 - 이미 참여한 유저")
    fun joinUser_BadRequest_User() {
        // given
        val uri = "/join"
        val body = JoinUserRequestDTO(
            inviteCode = "1Q8JOL07",
            joinUserId = 1
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    private fun performPost(uri: String, body: Any): MvcResult {
        val objectMapper = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
        val jsonBody = objectMapper.writeValueAsString(body)
        return mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody)
            )
            .andReturn()
    }

}