package com.tori.mementocaps.presentation.controller.apiController

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tori.mementocaps.presentation.dto.SignInDTO
import com.tori.mementocaps.presentation.requestDTO.CapsuleRequestDTO
import com.tori.mementocaps.presentation.requestDTO.RequestUserDTO
import com.tori.mementocaps.presentation.requestDTO.SignInRequestDTO
import com.tori.mementocaps.presentation.requestDTO.SignUpRequestDTO
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
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
class AuthApiControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    // NOTE: SIgnUp Test
    @Test
    @DisplayName("[SignUp] 정상적인 200 응답 반환 테스트")
    fun testSignUp_Success() {
        // given
        val uri = "/auth/signup"
        val body = SignUpRequestDTO(
            email = "email@email.com",
            password = "password1234",
            nickname = "닉네임"
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    @DisplayName("[SignUp] 400 응답 반환 테스트 - 이메일 형식 검증")
    fun testPostCapsuleList_BadRequest_Email() {
        // given
        val uri = "/auth/signup"
        val body = SignUpRequestDTO(
            email = "email123",
            password = "password1234",
            nickname = "닉네임"
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    @DisplayName("[Post - /capsule] 400 응답 반환 테스트 - 닉네임 글자수 검증")
    fun testPostCapsuleList_BadRequest_NickName() {
        // given
        val uri = "/auth/signup"
        val body = SignUpRequestDTO(
            email = "email@email.com",
            password = "password1234",
            nickname = "12345678901111"
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    @DisplayName("[Post - /capsule] 400 응답 반환 테스트 - 빈 값")
    fun testPostCapsuleList_BadRequest_Blank() {
        // given
        val uri = "/auth/signup"
        val body = SignUpRequestDTO(
            email = "email@email.com",
            password = "password1234",
            nickname = ""
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


    // NOTE: SIgnIn Test
    @Test
    @DisplayName("[SignIn] 정상적인 200 응답 반환 테스트")
    fun testSignIn_Success() {
        // given
        val uri = "/auth/signin"
        val body = SignInRequestDTO(
            email = "user2@email.com",
            password = "password123"
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    @DisplayName("[SignIn] 404 응답 반환 테스트 - 존재하지 않는 유저")
    fun testSignIn_NotFound() {
        // given
        val uri = "/auth/signin"
        val body = SignInRequestDTO(
            email = "user@email.com",
            password = "passowrd123"
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    @DisplayName("[SignIn] 400 응답 반환 테스트 - 이메일 형식")
    fun testSignIn_BadRequest() {
        // given
        val uri = "/auth/signin"
        val body = SignInRequestDTO(
            email = "user123",
            password = "passowrd123"
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }
}