package com.compprogserver.controller


import com.compprogserver.controller.request.GetUserHandlesRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.service.UserHandleService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
internal class UserHandleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var userHandleService: UserHandleService

    private val userHandleList = listOf(
            UserHandle(userHandle = "cfHandle", platform = Platform.CODEFORCES),
            UserHandle(userHandle = "ccHandle", platform = Platform.CODECHEF),
            UserHandle(userHandle = "kHandle", platform = Platform.KATTIS),
            UserHandle(userHandle = "uvaHandle", platform = Platform.UVA))

    private val username = "testUser"

    @Test
    fun `should return expected status and all user handles of user`() {

        every { userHandleService.getUserHandlesFromUsername(username) } returns userHandleList

        mockMvc.perform(post("/userhandle/get-userhandles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGetHandlesRequest()))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize<Any>(4)))
                .andExpect(jsonPath("$[0].userHandle").value("cfHandle"))
                .andExpect(jsonPath("$[1].userHandle").value("ccHandle"))
                .andExpect(jsonPath("$[2].userHandle").value("kHandle"))
                .andExpect(jsonPath("$[3].userHandle").value("uvaHandle"))
    }

    @Test
    fun `should return empty list when no user handles found for user`() {
        every { userHandleService.getUserHandlesFromUsername(username) } returns emptyList()

        mockMvc.perform(post("/userhandle/get-userhandles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGetHandlesRequest()))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isEmpty)
    }


    private fun createGetHandlesRequest(): String {
        val request = GetUserHandlesRequest(username = username)
        return objectMapper.writeValueAsString(request)
    }
}