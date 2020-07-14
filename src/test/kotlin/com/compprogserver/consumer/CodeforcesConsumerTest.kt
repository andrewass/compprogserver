package com.compprogserver.consumer

import com.compprogserver.entity.UserHandle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach

internal class CodeforcesConsumerTest {

    @MockK
    private lateinit var commonConsumer: CommonConsumer

    @InjectMockKs
    private lateinit var codeforcesConsumer: CodeforcesConsumer

    @BeforeEach
    private fun setUp() = MockKAnnotations.init(this)

    private val codeForcesUrl = "http://codeforces.com/api"

    private val userHandle = UserHandle(userHandle = "testUserHandle", rating = 1000,
            maxRating = 1500, rank = "150", maxRank = "100")

    fun `should return user handle for username`(){
        val url = "$codeForcesUrl/user/info"
        every {  }
    }

}