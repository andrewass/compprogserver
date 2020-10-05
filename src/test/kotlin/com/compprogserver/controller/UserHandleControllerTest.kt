package com.compprogserver.controller


import com.compprogserver.common.AbstractIntegrationTest
import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.controller.request.AddUserHandleRequest
import com.compprogserver.controller.request.GetUserHandlesRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
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

    @MockkBean
    private lateinit var codeforcesConsumerMock: CodeforcesConsumer

    private val userHandle = "testHandle"
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
    fun `should save manually created userhandle and return status ok`() {
        userRepository.save(user)

        mockMvc.perform(post("/userhandle/add-userhandle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddUserHandleRequest("Kattis")))
                .andExpect(status().isOk)

        val savedUserHandle = userHandleRepository
                .findByUserHandleAndPlatform(userHandle = userHandle, platform = Platform.KATTIS).get()

        assertEquals(userHandle, savedUserHandle.userHandle)
        assertEquals(Platform.KATTIS, savedUserHandle.platform)
    }

    @Test
    fun `should update manually created userhandle and return status ok`() {
        userRepository.save(user)

        val previousHandle = userHandleRepository.save(UserHandle(userHandle = userHandle, platform = Platform.KATTIS, user = user))

        mockMvc.perform(post("/userhandle/add-userhandle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddUserHandleRequest("Kattis")))
                .andExpect(status().isOk)

        val updatedHandle = userHandleRepository
                .findByUserHandleAndPlatform(userHandle = userHandle, platform = Platform.KATTIS).get()

        assertEquals(previousHandle.id, updatedHandle.id)
        assertEquals(previousHandle.userHandle, updatedHandle.userHandle)
        assertEquals(previousHandle.platform, updatedHandle.platform)
    }

    @Test
    fun `should fetch and save new codeforces userhandle and return status ok`() {
        userRepository.save(user)

        every {
            codeforcesConsumerMock.getUserHandle(userHandle, user)
        } returns UserHandle(userHandle = userHandle, rating = 1000, platform = Platform.CODEFORCES, user = user)

        mockMvc.perform(post("/userhandle/add-userhandle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddUserHandleRequest("Codeforces")))
                .andExpect(status().isOk)

        val savedUserHandle = userHandleRepository
                .findByUserHandleAndPlatform(userHandle = userHandle, platform = Platform.CODEFORCES).get()

        assertEquals(userHandle, savedUserHandle.userHandle)
        assertEquals(Platform.CODEFORCES, savedUserHandle.platform)
        assertEquals(1000, savedUserHandle.rating)
    }

    @Test
    fun `should fetch and update existing codeforces userhandle and return status ok`() {
        userRepository.save(user)

        val previousHandle = userHandleRepository.save(UserHandle(userHandle = userHandle,
                rating = 1500, platform = Platform.CODEFORCES, user = user))

        every {
            codeforcesConsumerMock.getUserHandle(userHandle, user)
        } returns UserHandle(userHandle = userHandle, rating = 1000, platform = Platform.CODEFORCES, user = user)

        mockMvc.perform(post("/userhandle/add-userhandle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddUserHandleRequest("Codeforces")))
                .andExpect(status().isOk)

        val updatedHandle = userHandleRepository
                .findByUserHandleAndPlatform(userHandle = userHandle, platform = Platform.CODEFORCES).get()

        assertEquals(previousHandle.id, updatedHandle.id)
        assertEquals(previousHandle.userHandle, updatedHandle.userHandle)
        assertEquals(previousHandle.platform, updatedHandle.platform)
        assertEquals(1000, updatedHandle.rating)
    }

    @Test
    fun `should return expected status and all user handles of user`() {
        connectUserAndUserHandles(userHandleList)

        mockMvc.perform(post("/userhandle/get-users-userhandles")
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

        mockMvc.perform(post("/userhandle/get-users-userhandles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGetHandlesRequest()))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isEmpty)
    }

    private fun createAddUserHandleRequest(platform: String): String {
        val request = AddUserHandleRequest(userHandle = userHandle, platform = platform, username = username)

        return objectMapper.writeValueAsString(request)
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