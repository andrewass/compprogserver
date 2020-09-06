package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.repository.ProblemRepository
import com.compprogserver.repository.SubmissionRepository
import com.compprogserver.repository.UserHandleRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeAll

internal class SubmissionServiceTest {

    @MockK
    private lateinit var submissionRepository: SubmissionRepository

    @MockK
    private lateinit var problemRepository: ProblemRepository

    @MockK
    private lateinit var userHandleRepository: UserHandleRepository

    @MockK
    private lateinit var codeforcesConsumer: CodeforcesConsumer

    @InjectMockKs
    private lateinit var submissionService: SubmissionService

    @BeforeAll
    private fun setUp() = MockKAnnotations.init(this)


}