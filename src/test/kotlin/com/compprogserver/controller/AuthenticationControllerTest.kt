package com.compprogserver.controller

import com.compprogserver.common.AbstractIntegrationTest
import com.compprogserver.controller.request.AuthenticationRequest
import com.compprogserver.controller.request.SignUpRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
internal class AuthenticationControllerTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val username = "testUser"
    private val email = "testEmail"
    private val password = "testPassword"

    @BeforeEach
    fun setupEach() {
        clearRepositories()
    }

    @Test
    fun `should successfully sign up new user`() {
        val resultActions = signUpUser()

        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("jwt").isString)
    }

    @Test
    fun `should not allow user to sign up twice`(){
        val firstResultActions = signUpUser()
        val secondResultActions = signUpUser()

        firstResultActions.andExpect(MockMvcResultMatchers.status().isOk)
        secondResultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `should successfully sign in user`() {
        signUpUser()

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAuthenticationRequestForUser()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("jwt").isString)
    }

    private fun createAuthenticationRequestForUser(): String {
        val request = AuthenticationRequest(username, password)
        return objectMapper.writeValueAsString(request)
    }

    private fun signUpUser(): ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createSignUpRequestForUser()))
    }

    private fun createSignUpRequestForUser(): String {
        val request = SignUpRequest(username, password, email)
        return objectMapper.writeValueAsString(request)
    }

}