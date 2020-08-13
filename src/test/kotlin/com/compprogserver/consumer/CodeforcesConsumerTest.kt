package com.compprogserver.consumer

import com.compprogserver.entity.UserHandle
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CodeforcesConsumerTest {

    @MockK
    private lateinit var restTemplate: RestTemplate

    @InjectMockKs
    private lateinit var codeforcesConsumer: CodeforcesConsumer

    @BeforeAll
    private fun setUp() = MockKAnnotations.init(this)

    private val gson = Gson()
    private val codeForcesUrl = "http://codeforces.com/api"
    private val testUser = "testUser"
    private val userHandle = UserHandle(userHandle = testUser, rating = 1000,
            maxRating = 1500, rank = "150", maxRank = "100")

    @Test
    fun `should return user handle for username`(){
        val userHandleUrl = "$codeForcesUrl/user.info?handles=$testUser"

        val userHandleJson = gson.toJson(userHandle).replace("userHandle","handle")
        val response = "{result : [$userHandleJson]}"

        every {
            restTemplate.exchange(userHandleUrl, HttpMethod.GET, any(), String::class.java)
        } returns ResponseEntity(response, HttpStatus.OK)

        val userHandle = codeforcesConsumer.getUserHandle(testUser)

        assertEquals(testUser, userHandle!!.userHandle)
    }

}