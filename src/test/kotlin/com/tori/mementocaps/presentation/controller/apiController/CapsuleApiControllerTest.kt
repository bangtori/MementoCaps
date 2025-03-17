package com.tori.mementocaps.presentation.controller.apiController

import org.assertj.core.api.Assertions
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[API Controller Test]")
class CapsuleApiControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    // NOTE: /capsule/list/{userId} API Test
    @Test
    @DisplayName("[/capsule/list/{userId}] 정상적인 200 응답 반환 테스트 - 캡슐 리스트가 비어있지 않을때")
    fun testGetCapsuleList_NotEmpty() {
        // given
        val uri = "/capsules/list/1"

        // when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)

        // then
        Assertions.assertThat(jsonArray.length()).isPositive()
    }

    @Test
    @DisplayName("[/capsule/list/{userId}] 정상적인 200 응답 반환 테스트 - 캡슐 리스트가 비어있을 때")
    fun testGetCapsuleList_Empty() {
        // given
        val uri = "/capsules/list/10"

        // when
        val mvcResult = performGet(uri)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonArray = JSONArray(contentAsString)

        // then
        Assertions.assertThat(jsonArray.length()).isEqualTo(0)
    }

    @Test
    @DisplayName("[/capsule/list/{userId}] 404에러 응답 반환 테스트 - uri 가 잘못됐을 때")
    fun testGetCapsuleList_NotFound() {
        // given
        val uri = "/capsule/list/1"

        // when
        val mvcResult = performGet(uri)

        // then
        Assertions.assertThat(mvcResult.response.status).isEqualTo(HttpStatus.NOT_FOUND.value())
    }


    // NOTE: /capsules/{capsuleId} API Test
    @Test
    @DisplayName("[/capsules/{capsuleId}] 정상적인 200 응답 반환 테스트 - role: OWNER")
    fun testGetCapsule_OWNER() {
        // given
        val uri = "/capsules/1"

        // when
        val mvcResult = performGetHeader(uri, 1)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonObject = JSONObject(contentAsString)

        // then
        Assertions.assertThat(jsonObject.optString("role")).isEqualTo("OWNER")
    }

    @Test
    @DisplayName("[/capsules/{capsuleId}] 정상적인 200 응답 반환 테스트 - role: MEMBER")
    fun testGetCapsule_MEMBER() {
        // given
        val uri = "/capsules/1"

        // when
        val mvcResult = performGetHeader(uri, 4)
        val contentAsString = mvcResult.response.getContentAsString(StandardCharsets.UTF_8)
        val jsonObject = JSONObject(contentAsString)

        // then
        Assertions.assertThat(jsonObject.optString("role")).isEqualTo("MEMBER")
    }

    @Test
    @DisplayName("[/capsules/{capsuleId}] 400 에러 반환 테스트")
    fun testGetCapsule_BadRequest() {
        // given
        val uri = "/capsules/1"

        // when
        val mvcResult = performGetHeader(uri, 10)


        // then
        Assertions.assertThat(mvcResult.response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    @DisplayName("[/capsules/{capsuleId}] 404 에러 반환 테스트")
    fun testGetCapsule_NOTFound() {
        // given
        val uri = "/capsules/100"

        // when
        val mvcResult = performGetHeader(uri, 1)

        // then
        Assertions.assertThat(mvcResult.response.status).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    private fun performGetHeader(uri: String, userId: Long): MvcResult {
        val headers = HttpHeaders().apply {
            this["userId"] = "${userId}"
        }

        return mockMvc
            .perform(
                MockMvcRequestBuilders.get(uri)
                .headers(headers)
            )
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    private fun performGet(uri: String): MvcResult {
        return mockMvc
            .perform(MockMvcRequestBuilders.get(uri))
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }
}