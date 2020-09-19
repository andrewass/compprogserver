package com.compprogserver.controller

import com.compprogserver.common.AbstractIntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc

@WithMockUser
@AutoConfigureMockMvc
internal class SubmissionControllerTest : AbstractIntegrationTest(){

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        clearRepositories()
    }

    @Test
    fun `should get all submissions from userhandle and status ok`(){
        assertEquals(1,1)
    }


}