package com.compprogserver.controller


import com.compprogserver.common.AbstractIntegrationTest
import com.compprogserver.controller.request.GetUserHandlesRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WithMockUser
@AutoConfigureMockMvc
internal class UserHandleControllerTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val username = "testUser"
    private val user = User(username = username)

    private val userHandleList = listOf(
            UserHandle(userHandle = "cfHandle", platform = Platform.CODEFORCES, user = user),
            UserHandle(userHandle = "ccHandle", platform = Platform.CODECHEF, user = user),
            UserHandle(userHandle = "kHandle", platform = Platform.KATTIS, user = user),
            UserHandle(userHandle = "uvaHandle", platform = Platform.UVA, user = user))


    @BeforeEach
    fun setup() {
        clearRepositories()
    }

    @Test
    fun `should return expected status and all user handles of user`() {
        connectUserAndUserHandles(userHandleList)

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
        connectUserAndUserHandles(emptyList())

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

    private fun connectUserAndUserHandles(userHandles: List<UserHandle>) {
        val user = userRepository.save(user)
        user.addUserHandles(userHandles)
        userRepository.save(user)
    }
}