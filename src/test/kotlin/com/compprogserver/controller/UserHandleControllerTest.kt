package com.compprogserver.controller


import com.compprogserver.common.CommonIntegrationTest
import com.compprogserver.controller.request.GetUserHandlesRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ActiveProfiles("test")
@WithMockUser
@AutoConfigureMockMvc
internal class UserHandleControllerTest : CommonIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val userHandleList = listOf(
            UserHandle(userHandle = "cfHandle", platform = Platform.CODEFORCES),
            UserHandle(userHandle = "ccHandle", platform = Platform.CODECHEF),
            UserHandle(userHandle = "kHandle", platform = Platform.KATTIS),
            UserHandle(userHandle = "uvaHandle", platform = Platform.UVA))

    private val username = "testUser"

    @BeforeEach
    fun setup(){
        userRepository.deleteAll()
        contestRepository.deleteAll()
    }

    @Test
    fun `should return expected status and all user handles of user`() {
        addUser(userHandleList)

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
        addUser(emptyList())

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

    private fun addUser(userHandles: List<UserHandle>) {
        val user = userRepository.save(User(username = username))
        userHandles.forEach { it.user = user }
        user.addUserHandles(userHandles)
        userRepository.save(user)
    }
}