package com.compprogserver.consumer

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

internal class CodeforcesConsumerTest {

    @MockK
    private lateinit var restTemplate: RestTemplate

    @InjectMockKs
    private lateinit var codeforcesConsumer: CodeforcesConsumer

    @BeforeEach
    private fun setUp() = MockKAnnotations.init(this)

    private val codeForcesUrl = "http://codeforces.com/api"


    fun `should get handle when successful response`() {
    }
}