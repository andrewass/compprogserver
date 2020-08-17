package com.compprogserver.consumer

import com.compprogserver.entity.Contest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.exception.ExternalEndpointException
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.ZoneOffset

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CodeforcesConsumerTest {

    @MockK
    private lateinit var restTemplate: RestTemplate

    @InjectMockKs
    private lateinit var codeforcesConsumer: CodeforcesConsumer

    @BeforeAll
    private fun setUp() = MockKAnnotations.init(this)

    private val gson = Gson()
    private val codeforcesUrl = "http://codeforces.com/api"

    private val testUser = "testUser"
    private val userHandleUrl = "$codeforcesUrl/user.info?handles=$testUser"
    private val userHandle = UserHandle(userHandle = testUser, rating = 1000,
            maxRating = 1500, rank = "150", maxRank = "100")

    private val constestUrl = "$codeforcesUrl/contest.list?gym=false"

    @Test
    fun `should return user handle for username`() {

        val userHandleJson = gson.toJson(userHandle).replace("userHandle", "handle")
        val response = "{result : [$userHandleJson]}"

        every {
            restTemplate.exchange(userHandleUrl, HttpMethod.GET, any(), String::class.java)
        } returns ResponseEntity(response, HttpStatus.OK)

        val userHandle = codeforcesConsumer.getUserHandle(testUser)

        assertEquals(testUser, userHandle!!.userHandle)
    }

    @Test
    fun `should throw exception when userhandle does not exist`() {
        every {
            restTemplate.exchange(userHandleUrl, HttpMethod.GET, any(), String::class.java)
        } returns ResponseEntity(HttpStatus.NOT_FOUND)

        assertThrows<ExternalEndpointException> { codeforcesConsumer.getUserHandle(testUser) }
    }

    @Test
    fun `should return list of contests`() {
        val today = LocalDateTime.now().withNano(0)
        val contestResponse = "{result : [{" +
                "id : 1000," +
                "name : testContest, " +
                "startTimeSeconds : ${today.toEpochSecond(ZoneOffset.UTC)}, " +
                "durationSeconds : 6000}]}"

        every {
            restTemplate.exchange(constestUrl, HttpMethod.GET, any(), String::class.java)
        } returns ResponseEntity(contestResponse, HttpStatus.OK)

        val contests = codeforcesConsumer.getContests() as HashSet<Contest>
        val contest = contests.first()

        assertEquals(1, contests.size)
        assertEquals("testContest", contest.contestName)
        assertEquals(1000, contest.remoteId)
        assertEquals(100, contest.duration)
        assertEquals(Platform.CODEFORCES, contest.platform)
        assertEquals(today, contest.startTime)
    }

    @Test
    fun `should return empty list when remote status not ok`() {
        every {
            restTemplate.exchange(constestUrl, HttpMethod.GET, any(), String::class.java)
        } returns ResponseEntity(HttpStatus.NOT_FOUND)

        val contests = codeforcesConsumer.getContests()

        assertTrue(contests.isEmpty())
    }
}