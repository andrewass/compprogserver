package com.compprogserver.consumer

import com.compprogserver.entity.Platform
import com.compprogserver.exception.ExternalEndpointException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UvaConsumerTest {

    @MockK
    private lateinit var restTemplate: RestTemplate

    @InjectMockKs
    private lateinit var uvaConsumer: UvaConsumer

    private val uhuntUrl = "https://uhunt.onlinejudge.org/api"

    @BeforeAll
    private fun setUp() = MockKAnnotations.init(this)

    private val testUser = "testUser"
    private val userHandleUrl = "$uhuntUrl/uname2uid/$testUser"
    private val externalId = "12345"


    @Test
    fun `should fetch and create userhandle`() {
        every {
            restTemplate.exchange(userHandleUrl, HttpMethod.GET, any(), String::class.java)
        } returns ResponseEntity(externalId, HttpStatus.OK)

        val userHandle = uvaConsumer.getUserHandle(testUser)

        assertEquals(externalId.toLong(), userHandle!!.externalId)
        assertEquals(Platform.UVA, userHandle.platform)
        assertEquals(testUser, userHandle.userHandle)
    }

    @Test
    fun `should throw exception when userhandle does not exist`() {
        every {
            restTemplate.exchange(userHandleUrl, HttpMethod.GET, any(), String::class.java)
        } returns ResponseEntity(HttpStatus.NOT_FOUND)

        assertThrows<ExternalEndpointException> { uvaConsumer.getUserHandle(testUser) }
    }
}