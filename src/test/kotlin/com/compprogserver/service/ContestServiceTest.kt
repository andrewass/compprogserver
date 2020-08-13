package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Contest
import com.compprogserver.repository.ContestRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ContestServiceTest {

    @MockK
    private lateinit var contestRepository: ContestRepository

    @MockK
    private lateinit var codeforcesConsumer: CodeforcesConsumer

    @InjectMockKs
    private lateinit var contestService: ContestService

    @BeforeAll
    private fun setUp() = MockKAnnotations.init(this)

    private val currentTime = LocalDateTime.now()

    private val upComingContests = listOf(
            Contest(startTime = currentTime.plusDays(8)),
            Contest(startTime = currentTime.plusDays(1)),
            Contest(startTime = currentTime.plusDays(5)),
            Contest(startTime = currentTime.plusDays(3)),
            Contest(startTime = currentTime.plusDays(6)))

    @Test
    fun `should return list of upcoming contests from service`() {
        every {
            contestRepository.findContestByStartTimeAfter(any())
        } returns upComingContests

        val contests = contestService.getUpcomingContests()

        assertEquals(5, contests.size)
        assertContestsAreSorted(contests)
    }

    @Test
    fun `should return empty list when no upcoming contests found`() {
        every {
            contestRepository.findContestByStartTimeAfter(any())
        } returns emptyList()

        val contests = contestService.getUpcomingContests()

        assertTrue(contests.isEmpty())
    }

    private fun assertContestsAreSorted(contests: List<Contest>) {
        for (i in 1 until contests.size) {
            assertTrue(contests[i - 1].startTime!!.isBefore(contests[i].startTime))
        }
    }
}