package com.tori.mementocaps.presentation.controller.apiController


import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tori.mementocaps.presentation.requestDTO.CapsuleRequestDTO
import com.tori.mementocaps.presentation.requestDTO.RequestUserDTO
import org.assertj.core.api.Assertions
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
import java.nio.charset.StandardCharsets
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[Capsule API Controller Test - POST/ DELETE Method]")
class CapsuleApiControllerPostDeleteTest(
    @Autowired private val mockMvc: MockMvc
) {
    // NOTE: Post - /capsule API Test
    @Test
    @DisplayName("[Post - /capsule] 정상적인 200 응답 반환 테스트")
    fun testPostCapsuleList_Success() {
        // given
        val uri = "/capsules"
        val body = CapsuleRequestDTO(
            title = "타임캡슐 테스트",
            content = "인프런 완주 하고 있니?",
            openDate = LocalDate.now().plusDays(15),
            wirterId = 1L
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    @DisplayName("[Post - /capsule] 400 에러 존재하지 않는 userId ")
    fun testPostCapsuleList_BadRequest() {
        // given
        val uri = "/capsules"
        val body = CapsuleRequestDTO(
            title = "타임캡슐 테스트",
            content = "인프런 완주 하고 있니?",
            openDate = LocalDate.now().plusDays(15),
            wirterId = 0L
        )

        // when
        val result = performPost(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    @DisplayName("[Post - /capsule] 400 에러 Validate 미통과 (과거 오픈 날짜 지정) ")
    fun testPostCapsuleList_BadRequest_OpenDateError() {
        // given
        val uri = "/capsules"
        val body = CapsuleRequestDTO(
            title = "타임캡슐 테스트",
            content = "인프런 완주 하고 있니?",
            openDate = LocalDate.now().minusDays(10),
            wirterId = 1L
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

    // NOTE: Delete - /capsule/{capsuleId} API Test
    @Test
    @DisplayName("[Delete - /capsule/{capsuleId}] 정상적인 200 응답 반환 테스트")
    fun testDeleteCapsuleList_Success() {
        // given
        val uri = "/capsules/1"
        val body = RequestUserDTO(
            requestUserId = 1L
        )

        // when
        val result = performDelete(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    @DisplayName("[Delete - /capsule/{capsuleId}] 403 에러 - 작성자가 아닌 멤버가 삭제")
    fun testDeleteCapsuleList_Forbidden_member() {
        // given
        val uri = "/capsules/1"
        val body = RequestUserDTO(
            requestUserId = 3L
        )

        // when
        val result = performDelete(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    @DisplayName("[Delete - /capsule/{capsuleId}] 403 에러 - 참여자가 아닌 유저가 삭제")
    fun testDeleteCapsuleList_Forbidden_noParticipant() {
        // given
        val uri = "/capsules/1"
        val body = RequestUserDTO(
            requestUserId = 2L
        )

        // when
        val result = performDelete(uri, body)

        // then
        Assertions.assertThat(result.response.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    private fun performDelete(uri: String, body: Any): MvcResult {
        val objectMapper = jacksonObjectMapper()
        val jsonBody = objectMapper.writeValueAsString(body)

        return mockMvc
            .perform(
                MockMvcRequestBuilders
                    .delete(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody)
            )
            .andReturn()
    }
}