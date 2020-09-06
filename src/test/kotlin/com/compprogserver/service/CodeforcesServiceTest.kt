package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.repository.ContestRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

internal class CodeforcesServiceTest {

    @MockK
    private lateinit var contestRepository: ContestRepository

    @MockK
    private lateinit var codeforcesConsumer: CodeforcesConsumer

    @InjectMockKs
    private lateinit var codeforcesService: CodeforcesService

    @BeforeAll
    private fun setUp() = MockKAnnotations.init(this)


}